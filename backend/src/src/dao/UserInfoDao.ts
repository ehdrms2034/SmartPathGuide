import { UserInfo } from "@models/UserInfo";
import { ApiResponse } from "@models/response";
import { CustomError } from "@models/CustomError";
import { userInfo } from "os";

export default class UserInfoDao {
  constructor() {}

  public async createUserInfo(memberPK: number) {
    const newUserInfo = UserInfo.build({
      userSeq: memberPK,
    });

    try {
      await newUserInfo.save();
    } catch (e) {
      if (e.name == "SequelizeUniqueConstraintError")
        throw new CustomError(
          e.name,
          "UserInfoDao 생성에 실패했습니다. : 중복된 userSeq값"
        );
    }
  }

  public async getAllUserInfo() {
    return await UserInfo.findAll();
  }

  public async getUserInfo(memberPK: number) {
    const userInfo = await UserInfo.findByPk(memberPK);
    if (!userInfo)
      throw new CustomError(
        "UserNotFound",
        "UserInfoDao - GetUserInfo 유저를 불러올 수 없습니다."
      );
    return userInfo;
  }

  public async setUserInfo(memberPK: number, reqUserInfo: UserInfo) {
    const userInfo = await this.getUserInfo(memberPK);
    userInfo.ancient = reqUserInfo.ancient;
    userInfo.medieval = reqUserInfo.medieval;
    userInfo.modern = reqUserInfo.modern;
    userInfo.donation = reqUserInfo.donation;
    userInfo.painting = reqUserInfo.painting;
    userInfo.world = reqUserInfo.world;
    userInfo.craft = reqUserInfo.craft;
    await userInfo.save();
  }
}
