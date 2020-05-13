import UserInfoDao from "@daos/UserInfoDao";
import PathsDao from "@daos/pathsDao";
import { UserInfo } from "@models/UserInfo";
import * as tf from "@tensorflow/tfjs-node";
import { userInfo } from "os";
import { layers, train, EarlyStopping } from "@tensorflow/tfjs-node";

class PathGuiderService {
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


  public async recommandAncient() {

    const trainSet = await this.getTrainSet();
    const testSet = await this.getTestSet();
    const trainPaths = await this.getTrainPathSet();
    const testPaths = await this.getTestPathSet();
    const target = trainPaths.map((it) => it.ancient);
    const testTarget = testPaths.map(it => it.ancient);

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

    //model.summary();
    await model.fit(input, output, {
      epochs: 300,
      batchSize : 70,
      shuffle: true,
      callbacks : [tf.callbacks.earlyStopping({monitor : 'acc'})]
    });

    // const scores = model.evaluate(xTest,yTest) as tf.Scalar[];
    // const acc = scores[1].dataSync();
    const predict = tf.tensor([[0,100,100,100,100,100,100]]);
    predict.print();
    // console.log(predict.shape);
    const test = model.predict(predict) as tf.Tensor;
    console.log("이사람이 갈 확률 :",test.dataSync()[0]);
    

    // console.log(acc[0]*100);
    // model.predict(trainSet);
  }

  public async getTrainPathSet() {
    const allPath = await this.pathsDao.getAllMemberPaths();
    return allPath.slice(0, Math.round(allPath.length * 0.8));
  }

  public async getTestPathSet(){
    const allPath = await this.pathsDao.getAllMemberPaths();
    return allPath.slice(Math.round(allPath.length * 0.8),allPath.length);
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

  //   public async findPathsByItemBase(
  //     memberPK: number,
  //     userInfoList: Array<UserInfo>
  //   ) {
  //     const { userInfoDao } = this;

  //     let similarity = -999;
  //     let userSeqNum = -1;

  //     let paths = await this.getAllUserInfo();
  //     let userInfo = await userInfoDao.getUserInfo(memberPK);

  //     userInfoList.map((it) => {
  //       if (it.userSeq !== memberPK) {
  //         let tempSimilarity;
  //         let numerator = 0;
  //         let denominator = 0;

  //         numerator += it["world"] * userInfo["world"];
  //         numerator += it["ancient"] * userInfo["ancient"];
  //         numerator += it["craft"] * userInfo["craft"];
  //         numerator += it["donation"] * userInfo["donation"];
  //         numerator += it["medieval"] * userInfo["medieval"];
  //         numerator += it["painting"] * userInfo["painting"];
  //         numerator += it["modern"] * userInfo["modern"];

  //         let firstDenomiator = Math.sqrt(
  //           Math.pow(it["world"], 2) +
  //             Math.pow(it["ancient"], 2) +
  //             Math.pow(it["craft"], 2) +
  //             Math.pow(it["donation"], 2) +
  //             Math.pow(it["medieval"], 2) +
  //             Math.pow(it["painting"], 2) +
  //             Math.pow(it["modern"], 2)
  //         );

  //         let secondDenomiator = Math.sqrt(
  //           Math.pow(userInfo["world"], 2) +
  //             Math.pow(userInfo["ancient"], 2) +
  //             Math.pow(userInfo["craft"], 2) +
  //             Math.pow(userInfo["donation"], 2) +
  //             Math.pow(userInfo["medieval"], 2) +
  //             Math.pow(userInfo["painting"], 2) +
  //             Math.pow(userInfo["modern"], 2)
  //         );

  //         denominator = firstDenomiator * secondDenomiator;

  //         tempSimilarity = numerator / denominator;
  //         if (tempSimilarity > similarity) {
  //           similarity = tempSimilarity;
  //           userSeqNum = it.userSeq;
  //         }
  //       }
  //     });

  //     const path = await this.pathsDao.getPaths(userSeqNum);
  //     return path.list;
  //   }
}

export default PathGuiderService;
