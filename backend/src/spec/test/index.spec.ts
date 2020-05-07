import { expect } from "chai";
import { sequelize } from "@models/index";
import UserInfoDao from "@daos/UserInfoDao";
import MemberDao from "@daos/memberDao";
import PathsDao from "@daos/pathsDao";
import { UserInfo } from "@models/UserInfo";
import PathGuideService from "@services/PathGuideService";
import { Disctricts } from "@models/Districts";
import { ENUM } from "sequelize/types";
import redisClient, { redisSetData, redisGetData } from '@modules/redis';

describe("DB세팅", () => {
  beforeAll(() => {
    sequelize;
    redisClient;
  });
  it("test", async () => {

    const pathGuideService = new PathGuideService();
    const infoList = await pathGuideService.getAllUserInfo();
    const pathList = await pathGuideService.findPathsByItemBase(15,infoList);
    redisSetData("hello",'helloss');
    redisGetData("hello")
    .then(data => 
      console.log(data)
      )
  });
});

// describe("DB세팅", () => {
//   beforeAll(() => {
//     sequelize;
//   });
//   it("test", async () => {
//     /* 데이터 Intialize */
//     for (let i = 0; i < 20000; i++) {
//       let randomAge = Math.round(Math.random() * 100) + 1;

//       const memberDao = new MemberDao();
//       const member = await memberDao.saveMember(randomAge);

//       const pathsDao = new PathsDao();
//       await pathsDao.createPaths(member);

//       do {
//         const randomDesti = Math.floor(Math.random() * 6);
//         const isInDestination = await pathsDao.isInDestination(
//           member.userSeq,
//           Disctricts[randomDesti]
//         );
//         console.log(isInDestination);
//         if (!isInDestination)
//           await pathsDao.addPaths(member.userSeq, Disctricts[randomDesti]);
//       } while (Math.floor(Math.random() * 10) !== 1);

//       const userInfoDao = new UserInfoDao();
//       const newUserInfo = new UserInfo();

//       newUserInfo.id = -1;
//       newUserInfo.ancient = Math.round(Math.random() * 100) + 1;
//       newUserInfo.medieval = Math.round(Math.random() * 100) + 1;
//       newUserInfo.modern = Math.round(Math.random() * 100) + 1;
//       newUserInfo.donation = Math.round(Math.random() * 100) + 1;
//       newUserInfo.painting = Math.round(Math.random() * 100) + 1;
//       newUserInfo.world = Math.round(Math.random() * 100) + 1;
//       newUserInfo.craft = Math.round(Math.random() * 100) + 1;
//       await userInfoDao.createUserInfo(member.userSeq);
//       await userInfoDao.setUserInfo(member.userSeq, newUserInfo);
//     }
//   });
// });
