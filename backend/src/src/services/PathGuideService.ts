import UserInfoDao from "@daos/UserInfoDao";
import PathsDao from "@daos/pathsDao";
import { UserInfo } from "@models/UserInfo";

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

  public async findPathsByItemBase(
    memberPK: number,
    userInfoList: Array<UserInfo>
  ) {
    const { userInfoDao } = this;

    let similarity = -999;
    let userSeqNum = -1;

    let paths = await this.getAllUserInfo();
    let userInfo = await userInfoDao.getUserInfo(memberPK);

    userInfoList.map((it) => {
      if (it.userSeq !== memberPK) {
        let tempSimilarity;
        let numerator = 0;
        let denominator = 0;

        numerator += it["world"] * userInfo["world"];
        numerator += it["ancient"] * userInfo["ancient"];
        numerator += it["craft"] * userInfo["craft"];
        numerator += it["donation"] * userInfo["donation"];
        numerator += it["medieval"] * userInfo["medieval"];
        numerator += it["painting"] * userInfo["painting"];
        numerator += it["modern"] * userInfo["modern"];

        let firstDenomiator = Math.sqrt(
          Math.pow(it["world"], 2) +
            Math.pow(it["ancient"], 2) +
            Math.pow(it["craft"], 2) +
            Math.pow(it["donation"], 2) +
            Math.pow(it["medieval"], 2) +
            Math.pow(it["painting"], 2) +
            Math.pow(it["modern"], 2)
        );

        let secondDenomiator = Math.sqrt(
          Math.pow(userInfo["world"], 2) +
            Math.pow(userInfo["ancient"], 2) +
            Math.pow(userInfo["craft"], 2) +
            Math.pow(userInfo["donation"], 2) +
            Math.pow(userInfo["medieval"], 2) +
            Math.pow(userInfo["painting"], 2) +
            Math.pow(userInfo["modern"], 2)
        );

        denominator = firstDenomiator * secondDenomiator;

        tempSimilarity = numerator / denominator;
        if (tempSimilarity > similarity) {
          similarity = tempSimilarity;
          userSeqNum = it.userSeq;
        }
      }
    });

    const path = await this.pathsDao.getPaths(userSeqNum);
    return path.list;
  }
}

export default PathGuiderService;
