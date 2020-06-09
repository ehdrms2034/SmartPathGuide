import UserInfoDao from "@daos/UserInfoDao";
import PathsDao from "@daos/pathsDao";
import { UserInfo } from "@models/UserInfo";
import * as tf from "@tensorflow/tfjs-node";
import { userInfo } from "os";
import {
  layers,
  train,
  EarlyStopping,
  reshape,
  softmax,
} from "@tensorflow/tfjs-node";
import path from "path";
import { Paths } from "@models/paths";

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
    const trainPaths = await this.getTrainPathSet();
    const testPaths = await this.getTestPathSet();
    const target = trainPaths.map((it) => {
      return [
        it.ancient,
        it.medieval,
        it.modern,
        it.donation,
        it.painting,
        it.world,
        it.craft,
        it.science,
        it.space,
        it.human,
        it.natural,
        it.future,
      ];
    });
    const testTarget = testPaths.map((it) => it.ancient);
    const input = reshape(tf.tensor(trainSet), [trainSet.length, 1, 12]);
    const output = reshape(tf.tensor(target), [target.length, 1, 12]);
    const xTest = tf.tensor(testSet);
    const yTest = tf.tensor(testTarget);
    const model = tf.sequential();

    model.add(tf.layers.lstm({
      units: 256,
      activation: "tanh",
      inputShape: [1, 12],
      returnSequences: true,
    }));

    model.add(tf.layers.dense({ units: 192, activation: "relu" }));

    model.add(tf.layers.dense({ units: 128, activation: "relu" }));

    model.add(tf.layers.dense({ units: 96, activation: "relu" }));

    model.add(tf.layers.dense({ units: 64, activation: "relu" }));

    model.add(tf.layers.dense({ units: 48, activation: "relu" }));

    model.add(tf.layers.dense({ units: 40, activation: "softmax" }));

    model.add(tf.layers.dense({ units: 36, activation: "relu" }));

    model.add(tf.layers.dense({ units: 24, activation: "relu" }));

    model.add(tf.layers.dense({ units: 12, activation: "sigmoid" }));

    model.compile({
      optimizer: tf.train.adam(0.001),
      loss: "binaryCrossentropy",
      metrics: ["accuracy"],
    });

    await model.fit(input, output, {
      epochs: 500,
      batchSize: 128,
      shuffle: true,
      callbacks: [tf.callbacks.earlyStopping({ monitor: "acc", patience: 10 })],
    });

    // const scores = model.evaluate(xTest,yTest) as tf.Scalar[];
    // const acc = scores[1].dataSync();
    const predict = reshape(
      tf.tensor([[60, 100, 30, 100, 100, 100, 100, 20, 30, 20, 50, 30]]),
      [1, 1, 12]
    );
    predict.print();
    // const test = model.predict(predict) as tf.Tensor;
    // console.log("이사람이 antient 갈 확률 :", test.dataSync()[0]);
    // console.log(test.dataSync());
    model.save("file://./src/TensorModel/model");

    // console.log(acc[0]*100);
    // model.predict(trainSet);
  }


  public async predictPaths(tensor: tf.Tensor) {
    const {USER_RECOMMAND_PERCENT} = this;
    const model = await tf.loadLayersModel(
      "file://./src/TensorModel/model/model.json"
    );
    const predict = model.predict(tensor) as tf.Tensor;
    const result = predict.dataSync();
    const data = {
      isAncient : result[0] >= USER_RECOMMAND_PERCENT,
      isMedieval : result[1] >= USER_RECOMMAND_PERCENT,
      isModern : result[2] >= USER_RECOMMAND_PERCENT,
      isDonation : result[3] >= USER_RECOMMAND_PERCENT,
      isPainting : result[4] >= USER_RECOMMAND_PERCENT,
      isWorld : result[5] >= USER_RECOMMAND_PERCENT,
      isCraft : result[6] >= USER_RECOMMAND_PERCENT,
      isScience : result[7] >= USER_RECOMMAND_PERCENT,
      isSpace : result[8] >= USER_RECOMMAND_PERCENT,
      isHuman : result[9] >= USER_RECOMMAND_PERCENT,
      isNatural : result[10] >= USER_RECOMMAND_PERCENT,
      isFuture : result[11] >= USER_RECOMMAND_PERCENT
    }
    return data;
  }

  public async getTrainPathSet() {
    const allPath = await this.pathsDao.getAllMemberPaths();
    return allPath.slice(0, Math.round(allPath.length * 0.8));
  }

  public async getTestPathSet() {
    const allPath = await this.pathsDao.getAllMemberPaths();
    return allPath.slice(Math.round(allPath.length * 0.8), allPath.length);
  }

  public async getTrainSet() {
    const { userInfoDao, pathsDao } = this;
    const allUserInfo = await userInfoDao.getAllUserInfo();
    const allPaths = await pathsDao.getAllMemberPaths();
    const mapList: Array<any> = [];
    const maxNum = Math.round(allPaths.length * 0.8);
    for (let i = 0; i < maxNum; i++) {
      const userInfo = allUserInfo[i];
      const userPath = allPaths[i];
      mapList.push([
        userInfo.ancient,
        userInfo.medieval,
        userInfo.modern,
        userInfo.donation,
        userInfo.painting,
        userInfo.world,
        userInfo.craft,
        userInfo.science,
        userInfo.space,
        userInfo.human,
        userInfo.natural,
        userInfo.future,
      ]);
    }
    return mapList;
  }

  public async getTestSet() {
    const { userInfoDao, pathsDao } = this;
    const allUserInfo = await userInfoDao.getAllUserInfo();
    const allPaths = await pathsDao.getAllMemberPaths();
    const mapList: Array<any> = [];
    const maxNum = Math.round(allPaths.length * 0.8);
    for (let i = maxNum; i < allPaths.length; i++) {
      const userInfo = allUserInfo[i];
      const userPath = allPaths[i];
      mapList.push([
        userInfo.ancient,
        userInfo.medieval,
        userInfo.modern,
        userInfo.donation,
        userInfo.painting,
        userInfo.world,
        userInfo.craft,
        userInfo.science,
        userInfo.space,
        userInfo.human,
        userInfo.natural,
        userInfo.future,
      ]);
    }
    return mapList;
  }
}

export default PathRecommendService;
