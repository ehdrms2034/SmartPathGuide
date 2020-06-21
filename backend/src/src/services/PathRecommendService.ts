import UserInfoDao from "@daos/UserInfoDao";
import PathsDao from "@daos/pathsDao";
import { UserInfo } from "@models/UserInfo";
import * as tf from "@tensorflow/tfjs-node";
import { userInfo } from "os";
import fs from "fs";

import {
  layers,
  train,
  EarlyStopping,
  reshape,
  softmax,
} from "@tensorflow/tfjs-node";
import path from "path";
import { Paths } from "@models/paths";
import PlaceDao from '@daos/PlaceDao';

class PathRecommendService {
  private USER_RECOMMAND_PERCENT = 0.5;
  private INPUT_SHAPE = 12;

  private userInfoDao: UserInfoDao;
  private pathsDao: PathsDao;

  constructor() {
    this.userInfoDao = new UserInfoDao();
    this.pathsDao = new PathsDao();
  }

  public async getAllUserInfo() {
    const { userInfoDao } = this;
    let paths = await userInfoDao.getAllUserInfo();
    return paths;
  }

  public async makeRecommendModel() {
    const trainSet = await this.getTrainSet();
    const testSet = await this.getTestSet();
    const inputPath = trainSet[0];
    const outputPath = trainSet[1];
    const testInputPath = testSet[0];
    const testOutputPath = testSet[1];
    let max = -1;
    console.log();

    fs.writeFileSync("./inputPath.json", JSON.stringify(inputPath), "utf8");
    fs.writeFileSync("./outputPath.json", JSON.stringify(outputPath), "utf8");
    fs.writeFileSync(
      "./testInputPath.json",
      JSON.stringify(testInputPath),
      "utf8"
    );
    fs.writeFileSync(
      "./testOutputPath.json",
      JSON.stringify(testOutputPath),
      "utf8"
    );

    const input = reshape(tf.tensor(inputPath), [inputPath.length, 12, 2]);
    const output = reshape(tf.tensor(outputPath), [outputPath.length, 13]);
    input.print();
    output.print();
    const xTest = reshape(tf.tensor(testInputPath), [
      testInputPath.length,
      12,
      2,
    ]);
    const yTest = reshape(tf.tensor(testOutputPath), [
      testOutputPath.length,
      13,
    ]);
    const model = tf.sequential();

    model.add(
      tf.layers.lstm({
        units: 128,
        activation: "tanh",
        inputShape: [12, 2],
        batchInputShape: [1, 12, 2],
      })
    );

    model.add(tf.layers.dense({ units: 96, activation: "relu" }));
    model.add(tf.layers.dense({ units: 64, activation: "relu" }));
    model.add(tf.layers.dense({ units: 48, activation: "relu" }));
    model.add(tf.layers.dense({ units: 40, activation: "relu" }));
    model.add(tf.layers.dense({ units: 36, activation: "relu" }));
    model.add(tf.layers.dense({ units: 13, activation: "sigmoid" }));

    model.compile({
      optimizer: tf.train.adam(0.001),
      loss: "binaryCrossentropy",
      metrics: ["accuracy"],
    });

    await model.fit(input, output, {
      epochs: 500,
      batchSize: 32,
      shuffle: false,
      stepsPerEpoch: 10,
      callbacks: [tf.callbacks.earlyStopping({ monitor: "acc", patience: 3 })],
      validationData: [xTest, yTest],
      verbose: 1,
    });

    const index = 15;
    const predata = tf.tensor(inputPath[index]);
    predata.print();
    const predict = reshape(predata, [1, 12, 2]);
    predict.print();
    const test = model.predict(predict) as tf.Tensor;
    test.print();
    tf.argMax(test.dataSync()).print();
    await model.save("file://./src/TensorModel/model");
  }

  public async predictPaths(tensor: tf.Tensor) {
    const { USER_RECOMMAND_PERCENT } = this;
    const model = await tf.loadLayersModel(
      "file://./src/TensorModel/model/model.json"
    );
    const placeDao = new PlaceDao();
    const places = placeDao.getPlaces();
    const predict = model.predict(tensor) as tf.Tensor;
    const result = predict.dataSync();
    const list = [];
    for (let i = 0; i < result.length; i++) {
      list.push({ placeId: i, name: this.placeId2Name(i),percent: result[i] });
    }
    list.sort((a, b) => {
      return a.percent < b.percent ? 1 : a.percent > b.percent ? -1 : 0;
    });

    return list;
  }

  public placeId2Name(placeId: number) {
    switch (placeId) {
      case 0:
        return "ENTRANCE";
      case 1:
        return "ANCIENT";
      case 2:
        return "MEDIEVAL";
      case 3:
        return "MODERN";
      case 4:
        return "DONATION";
      case 5:
        return "PAINTING";
      case 6:
        return "WORLD";
      case 7:
        return "CRAFT";
      case 8:
        return "SCIENCE";
      case 9:
        return "SPACE";
      case 10:
        return "HUMAN";
      case 11:
        return "NATURAL";
      case 12:
        return "FUTURE";
    }
  }

  public async getTrainSet() {
    const { userInfoDao, pathsDao } = this;
    const allPaths = await pathsDao.getAllMemberPaths();
    const mapList: Array<any> = [];
    const maxNum = Math.round(allPaths.length * 0.1);
    //[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
    let list = [
      [0, 0],
      [0, 0],
      [0, 0],
      [0, 0],
      [0, 0],
      [0, 0],
      [0, 0],
      [0, 0],
      [0, 0],
      [0, 0],
      [0, 0],
      [0, 0],
    ];
    for (let i = 0; i < maxNum; i++) {
      const userPath = allPaths[i];

      // list[userPath.sequence] = (userPath.sequence+1)/13;
      list[userPath.sequence][0] = userPath.placeId;
      list[userPath.sequence][1] = userPath.stayTime;
      // list[userPath.sequence][2] = userPath.stayTime/360;
      let data = await JSON.parse(JSON.stringify(list));
      if (allPaths[i].userSeq === allPaths[i + 1].userSeq) mapList.push(data);
      else
        list = [
          [0, 0],
          [0, 0],
          [0, 0],
          [0, 0],
          [0, 0],
          [0, 0],
          [0, 0],
          [0, 0],
          [0, 0],
          [0, 0],
          [0, 0],
          [0, 0],
        ];
    }

    const nextData = [];
    for (let i = 0; i < maxNum; i++) {
      const userPath = allPaths[i + 1];
      const next = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
      next[userPath.placeId] = 1;
      if (allPaths[i].userSeq === allPaths[i + 1].userSeq) nextData.push(next);
    }

    const result = [mapList, nextData];
    return result;
  }

  public async getTestSet() {
    const { userInfoDao, pathsDao } = this;
    const allPaths = await pathsDao.getAllMemberPaths();
    const mapList: Array<any> = [];
    const maxNum = Math.round(allPaths.length * 0.95);
    let list = [
      [0, 0],
      [0, 0],
      [0, 0],
      [0, 0],
      [0, 0],
      [0, 0],
      [0, 0],
      [0, 0],
      [0, 0],
      [0, 0],
      [0, 0],
      [0, 0],
    ];
    for (let i = maxNum; i < allPaths.length - 1; i++) {
      const userPath = allPaths[i];

      // list[userPath.sequence][0] = (userPath.sequence+1)/13;
      list[userPath.sequence][0] = userPath.placeId;
      list[userPath.sequence][1] = userPath.stayTime;
      // list[userPath.sequence][2] = userPath.stayTime/360;
      let data = await JSON.parse(JSON.stringify(list));
      if (allPaths[i].userSeq === allPaths[i + 1].userSeq) mapList.push(data);
      else
        list = [
          [0, 0],
          [0, 0],
          [0, 0],
          [0, 0],
          [0, 0],
          [0, 0],
          [0, 0],
          [0, 0],
          [0, 0],
          [0, 0],
          [0, 0],
          [0, 0],
        ];
    }

    const nextData = [];
    for (let i = maxNum; i < allPaths.length - 1; i++) {
      const userPath = allPaths[i + 1];
      const next = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
      next[userPath.placeId] = 1;
      if (allPaths[i].userSeq === allPaths[i + 1].userSeq) nextData.push(next);
    }

    const result = [mapList, nextData];
    return result;
  }
}

export default PathRecommendService;
