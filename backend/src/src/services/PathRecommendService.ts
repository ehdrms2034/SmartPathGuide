import UserInfoDao from "@daos/UserInfoDao";
import PathsDao from "@daos/pathsDao";
import { UserInfo } from "@models/UserInfo";
import * as tf from "@tensorflow/tfjs-node";
import { userInfo } from "os";
import { layers, train, EarlyStopping } from "@tensorflow/tfjs-node";
import path from "path";

class PathRecommendService {
  private USER_RECOMMAND_PERCENT = 0.5;

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

  public async makeAncientModel() {
    const trainSet = await this.getTrainSet();
    const testSet = await this.getTestSet();
    const trainPaths = await this.getTrainPathSet();
    const testPaths = await this.getTestPathSet();
    const target = trainPaths.map((it) => it.ancient);
    const testTarget = testPaths.map((it) => it.ancient);

    const input = tf.tensor(trainSet);
    const output = tf.tensor(target);

    const xTest = tf.tensor(testSet);
    const yTest = tf.tensor(testTarget);

    const model = tf.sequential({
      layers: [
        tf.layers.dense({ units: 64, inputShape: [7] }),
        tf.layers.dense({ units: 8, activation: "relu" }),
        tf.layers.dense({ units: 1, activation: "sigmoid" }),
      ],
    });

    model.compile({
      optimizer: tf.train.adam(),
      loss: "binaryCrossentropy",
      metrics: ["accuracy"],
    });

    await model.fit(input, output, {
      epochs: 300,
      batchSize: 70,
      shuffle: true,
      callbacks: [tf.callbacks.earlyStopping({ monitor: "acc" })],
    });

    // const scores = model.evaluate(xTest,yTest) as tf.Scalar[];
    // const acc = scores[1].dataSync();
    const predict = tf.tensor([[0, 100, 100, 100, 100, 100, 100]]);
    predict.print();
    // console.log(predict.shape);
    const test = model.predict(predict) as tf.Tensor;
    console.log("이사람이 antient 갈 확률 :", test.dataSync()[0]);

    model.save("file://./src/TensorModel/ancient");

    // console.log(acc[0]*100);
    // model.predict(trainSet);
  }

  public async makeMedievalModel() {
    const trainSet = await this.getTrainSet();
    const testSet = await this.getTestSet();
    const trainPaths = await this.getTrainPathSet();
    const testPaths = await this.getTestPathSet();
    const target = trainPaths.map((it) => it.medieval);
    const testTarget = testPaths.map((it) => it.medieval);

    const input = tf.tensor(trainSet);
    const output = tf.tensor(target);

    const xTest = tf.tensor(testSet);
    const yTest = tf.tensor(testTarget);

    const model = tf.sequential({
      layers: [
        tf.layers.dense({ units: 64, inputShape: [7] }),
        tf.layers.dense({ units: 8, activation: "relu" }),
        tf.layers.dense({ units: 1, activation: "sigmoid" }),
      ],
    });

    model.compile({
      optimizer: tf.train.adam(),
      loss: "binaryCrossentropy",
      metrics: ["accuracy"],
    });

    await model.fit(input, output, {
      epochs: 300,
      batchSize: 70,
      shuffle: true,
      callbacks: [tf.callbacks.earlyStopping({ monitor: "acc" })],
    });

    // const scores = model.evaluate(xTest,yTest) as tf.Scalar[];
    // const acc = scores[1].dataSync();
    const predict = tf.tensor([[0, 100, 100, 100, 100, 100, 100]]);
    predict.print();
    // console.log(predict.shape);
    const test = model.predict(predict) as tf.Tensor;
    console.log("이사람이 medieval 갈 확률 :", test.dataSync()[0]);

    model.save("file://./src/TensorModel/medieval");

    // console.log(acc[0]*100);
    // model.predict(trainSet);
  }

  public async makeModernModel() {
    const trainSet = await this.getTrainSet();
    const testSet = await this.getTestSet();
    const trainPaths = await this.getTrainPathSet();
    const testPaths = await this.getTestPathSet();
    const target = trainPaths.map((it) => it.modern);
    const testTarget = testPaths.map((it) => it.modern);

    const input = tf.tensor(trainSet);
    const output = tf.tensor(target);

    const xTest = tf.tensor(testSet);
    const yTest = tf.tensor(testTarget);

    const model = tf.sequential({
      layers: [
        tf.layers.dense({ units: 64, inputShape: [7] }),
        tf.layers.dense({ units: 8, activation: "relu" }),
        tf.layers.dense({ units: 1, activation: "sigmoid" }),
      ],
    });

    model.compile({
      optimizer: tf.train.adam(),
      loss: "binaryCrossentropy",
      metrics: ["accuracy"],
    });

    await model.fit(input, output, {
      epochs: 300,
      batchSize: 70,
      shuffle: true,
      callbacks: [tf.callbacks.earlyStopping({ monitor: "acc" })],
    });

    // const scores = model.evaluate(xTest,yTest) as tf.Scalar[];
    // const acc = scores[1].dataSync();
    const predict = tf.tensor([[0, 100, 100, 100, 100, 100, 100]]);
    predict.print();
    // console.log(predict.shape);
    const test = model.predict(predict) as tf.Tensor;
    console.log("이사람이 modern 갈 확률 :", test.dataSync()[0]);

    model.save("file://./src/TensorModel/modern");

    // console.log(acc[0]*100);
    // model.predict(trainSet);
  }

  public async makePaintingModel() {
    const trainSet = await this.getTrainSet();
    const testSet = await this.getTestSet();
    const trainPaths = await this.getTrainPathSet();
    const testPaths = await this.getTestPathSet();
    const target = trainPaths.map((it) => it.painting);
    const testTarget = testPaths.map((it) => it.painting);

    const input = tf.tensor(trainSet);
    const output = tf.tensor(target);

    const xTest = tf.tensor(testSet);
    const yTest = tf.tensor(testTarget);

    const model = tf.sequential({
      layers: [
        tf.layers.dense({ units: 64, inputShape: [7] }),
        tf.layers.dense({ units: 8, activation: "relu" }),
        tf.layers.dense({ units: 1, activation: "sigmoid" }),
      ],
    });

    model.compile({
      optimizer: tf.train.adam(),
      loss: "binaryCrossentropy",
      metrics: ["accuracy"],
    });

    await model.fit(input, output, {
      epochs: 300,
      batchSize: 70,
      shuffle: true,
      callbacks: [tf.callbacks.earlyStopping({ monitor: "acc" })],
    });

    // const scores = model.evaluate(xTest,yTest) as tf.Scalar[];
    // const acc = scores[1].dataSync();
    const predict = tf.tensor([[0, 100, 100, 100, 100, 100, 100]]);
    predict.print();
    // console.log(predict.shape);
    const test = model.predict(predict) as tf.Tensor;
    console.log("이사람이 painting 갈 확률 :", test.dataSync()[0]);

    model.save("file://./src/TensorModel/painting");

    // console.log(acc[0]*100);
    // model.predict(trainSet);
  }

  public async makeWorldModel() {
    const trainSet = await this.getTrainSet();
    const testSet = await this.getTestSet();
    const trainPaths = await this.getTrainPathSet();
    const testPaths = await this.getTestPathSet();
    const target = trainPaths.map((it) => it.world);
    const testTarget = testPaths.map((it) => it.world);

    const input = tf.tensor(trainSet);
    const output = tf.tensor(target);

    const xTest = tf.tensor(testSet);
    const yTest = tf.tensor(testTarget);

    const model = tf.sequential({
      layers: [
        tf.layers.dense({ units: 64, inputShape: [7] }),
        tf.layers.dense({ units: 8, activation: "relu" }),
        tf.layers.dense({ units: 1, activation: "sigmoid" }),
      ],
    });

    model.compile({
      optimizer: tf.train.adam(),
      loss: "binaryCrossentropy",
      metrics: ["accuracy"],
    });

    await model.fit(input, output, {
      epochs: 300,
      batchSize: 70,
      shuffle: true,
      callbacks: [tf.callbacks.earlyStopping({ monitor: "acc" })],
    });

    // const scores = model.evaluate(xTest,yTest) as tf.Scalar[];
    // const acc = scores[1].dataSync();
    const predict = tf.tensor([[0, 100, 100, 100, 100, 100, 100]]);
    predict.print();
    // console.log(predict.shape);
    const test = model.predict(predict) as tf.Tensor;
    console.log("이사람이 world 갈 확률 :", test.dataSync()[0]);

    model.save("file://./src/TensorModel/world");

    // console.log(acc[0]*100);
    // model.predict(trainSet);
  }

  public async makeCraftModel() {
    const trainSet = await this.getTrainSet();
    const testSet = await this.getTestSet();
    const trainPaths = await this.getTrainPathSet();
    const testPaths = await this.getTestPathSet();
    const target = trainPaths.map((it) => it.craft);
    const testTarget = testPaths.map((it) => it.craft);

    const input = tf.tensor(trainSet);
    const output = tf.tensor(target);

    const xTest = tf.tensor(testSet);
    const yTest = tf.tensor(testTarget);

    const model = tf.sequential({
      layers: [
        tf.layers.dense({ units: 64, inputShape: [7] }),
        tf.layers.dense({ units: 8, activation: "relu" }),
        tf.layers.dense({ units: 1, activation: "sigmoid" }),
      ],
    });

    model.compile({
      optimizer: tf.train.adam(),
      loss: "binaryCrossentropy",
      metrics: ["accuracy"],
    });

    await model.fit(input, output, {
      epochs: 300,
      batchSize: 70,
      shuffle: true,
      callbacks: [tf.callbacks.earlyStopping({ monitor: "acc" })],
    });

    // const scores = model.evaluate(xTest,yTest) as tf.Scalar[];
    // const acc = scores[1].dataSync();
    const predict = tf.tensor([[0, 100, 100, 100, 100, 100, 100]]);
    predict.print();
    // console.log(predict.shape);
    const test = model.predict(predict) as tf.Tensor;
    console.log("이사람이 craft 갈 확률 :", test.dataSync()[0]);

    model.save("file://./src/TensorModel/craft");

    // console.log(acc[0]*100);
    // model.predict(trainSet);
  }

  public async makeDonationModel() {
    const trainSet = await this.getTrainSet();
    const testSet = await this.getTestSet();
    const trainPaths = await this.getTrainPathSet();
    const testPaths = await this.getTestPathSet();
    const target = trainPaths.map((it) => it.donation);
    const testTarget = testPaths.map((it) => it.donation);

    const input = tf.tensor(trainSet);
    const output = tf.tensor(target);

    const xTest = tf.tensor(testSet);
    const yTest = tf.tensor(testTarget);

    const model = tf.sequential({
      layers: [
        tf.layers.dense({ units: 64, inputShape: [7] }),
        tf.layers.dense({ units: 8, activation: "relu" }),
        tf.layers.dense({ units: 1, activation: "sigmoid" }),
      ],
    });

    model.compile({
      optimizer: tf.train.adam(),
      loss: "binaryCrossentropy",
      metrics: ["accuracy"],
    });

    await model.fit(input, output, {
      epochs: 300,
      batchSize: 70,
      shuffle: true,
      callbacks: [tf.callbacks.earlyStopping({ monitor: "acc" })],
    });

    // const scores = model.evaluate(xTest,yTest) as tf.Scalar[];
    // const acc = scores[1].dataSync();
    const predict = tf.tensor([[0, 100, 100, 100, 100, 100, 100]]);
    predict.print();
    // console.log(predict.shape);
    const test = model.predict(predict) as tf.Tensor;
    console.log("이사람이 donation 갈 확률 :", test.dataSync()[0]);

    model.save("file://./src/TensorModel/donation");

    // console.log(acc[0]*100);
    // model.predict(trainSet);
  }

  public async recommandAntient(tensor: tf.Tensor) {
    const model = await tf.loadLayersModel(
      "file://./src/TensorModel/ancient/model.json"
    );
    const predict = model.predict(tensor) as tf.Tensor;
    const result = predict.dataSync()[0];
    console.log(result);

    if (result >= this.USER_RECOMMAND_PERCENT) return true;
    else return false;
  }

  public async recommandMedieval(tensor: tf.Tensor) {
    const model = await tf.loadLayersModel(
      "file://./src/TensorModel/medieval/model.json"
    );
    const predict = model.predict(tensor) as tf.Tensor;
    const result = predict.dataSync()[0];
    console.log(result);

    if (result >= this.USER_RECOMMAND_PERCENT) return true;
    else return false;
  }

  public async recommandModern(tensor: tf.Tensor) {
    const model = await tf.loadLayersModel(
      "file://./src/TensorModel/modern/model.json"
    );
    const predict = model.predict(tensor) as tf.Tensor;
    const result = predict.dataSync()[0];
    console.log(result);

    if (result >= this.USER_RECOMMAND_PERCENT) return true;
    else return false;
  }

  public async recommandDonation(tensor: tf.Tensor) {
    const model = await tf.loadLayersModel(
      "file://./src/TensorModel/donation/model.json"
    );
    const predict = model.predict(tensor) as tf.Tensor;
    const result = predict.dataSync()[0];
    console.log(result);
    if (result >= this.USER_RECOMMAND_PERCENT) return true;
    else return false;
  }

  public async recommandPainting(tensor: tf.Tensor) {
    const model = await tf.loadLayersModel(
      "file://./src/TensorModel/painting/model.json"
    );
    const predict = model.predict(tensor) as tf.Tensor;
    const result = predict.dataSync()[0];
    console.log(result);
    if (result >= this.USER_RECOMMAND_PERCENT) return true;
    else return false;
  }

  public async recommandWorld(tensor: tf.Tensor) {
    const model = await tf.loadLayersModel(
      "file://./src/TensorModel/world/model.json"
    );
    const predict = model.predict(tensor) as tf.Tensor;
    const result = predict.dataSync()[0];
    console.log(result);

    if (result >= this.USER_RECOMMAND_PERCENT) return true;
    else return false;
  }

  public async recommandCraft(tensor: tf.Tensor) {
    const model = await tf.loadLayersModel(
      "file://./src/TensorModel/craft/model.json"
    );
    const predict = model.predict(tensor) as tf.Tensor;
    const result = predict.dataSync()[0];
    console.log(result);

    if (result >= this.USER_RECOMMAND_PERCENT) return true;
    else return false;
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
      ]);
    }
    return mapList;
  }
}

export default PathRecommendService;
