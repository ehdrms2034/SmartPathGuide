import { expect } from "chai";
import { sequelize } from "@models/index";
import UserInfoDao from "@daos/UserInfoDao";
import MemberDao from "@daos/memberDao";
import PathsDao, { RequestPath } from "@daos/pathsDao";
import { UserInfo } from "@models/UserInfo";
import PathGuideService from "@services/PathGuideService";
import { Disctricts } from "@models/Districts";
import { ENUM } from "sequelize/types";
import redisClient, { redisSetData, redisGetData } from "@modules/redis";
import { Paths } from "@models/paths";
import * as tf from "@tensorflow/tfjs-node";
import UserLocationService from '@services/UserLocationService';

// describe("DB세팅", () => {
//   beforeAll(() => {
//     sequelize;
//     redisClient;
//   });
//   it("test", async () => {

//     const pathGuideService = new PathGuideService();
//     const infoList = await pathGuideService.getAllUserInfo();
//     const pathList = await pathGuideService.findPathsByItemBase(15,infoList);
//     redisSetData("hello",'helloss');
//     redisGetData("hello")
//     .then(data =>
//       console.log(data)
//       )
//   });
// });

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

//       const pathsDao = new PathsDao();

//       const isAncient = Math.round(Math.random()*100 / newUserInfo.ancient) <= 1 ? 1 : 0;
//       const isMedieval = Math.round(Math.random()*100 / newUserInfo.medieval) <= 1 ? 1 : 0;
//       const isModern = Math.round(Math.random()*100 / newUserInfo.modern) <= 1 ? 1 : 0;
//       const isDonation = Math.round(Math.random()*100 / newUserInfo.donation) <= 1 ? 1 : 0;
//       const isPainting = Math.round(Math.random()*100 / newUserInfo.painting) <= 1 ? 1 : 0;
//       const isWorld = Math.round(Math.random()*100 / newUserInfo.world) <= 1 ? 1 : 0;
//       const isCraft = Math.round(Math.random()*100 / newUserInfo.craft) <= 1 ? 1 : 0;

//       const reqPaths :RequestPath = {
//         ancient : isAncient,
//         medieval : isMedieval,
//         modern : isModern,
//         donation : isDonation,
//         painting : isPainting,
//         world : isWorld,
//         craft : isCraft
//       };

//       await pathsDao.createPaths(member);
//       await pathsDao.setPaths(member.userSeq, reqPaths);

//     }
//   });
// });

// describe("Tensorflow",()=>{
//   beforeAll(()=>{
//     sequelize;
//     tf;
//   });
//   it("tesorTuto",async ()=>{
//     const pathGuideService = new PathGuideService();
//     await pathGuideService.makeAncientModel();
//     await pathGuideService.makeMedievalModel();
//     await pathGuideService.makeModernModel();
//     await pathGuideService.makePaintingModel();
//     await pathGuideService.makeDonationModel();
//     await pathGuideService.makeWorldModel();
//     await pathGuideService.makeCraftModel();

//     //tf.tensor([1,2]).print();
//   },2000000);
// })

// describe("Tensorflow", () => {
//   beforeAll(() => {
//     sequelize;
//     tf;
//   });
//   it("tesorTuto", async () => {
//     const pathGuideService = new PathGuideService();
//     const testData = tf.tensor([[30, 30, 60, 80, 80, 40, 70]]);
//     const isAncient = await pathGuideService.recommandAntient(testData);
//     const isMedieval = await pathGuideService.recommandMedieval(testData);
//     const isModern = await pathGuideService.recommandModern(testData);
//     const isDonation = await pathGuideService.recommandDonation(testData);
//     const isPainting = await pathGuideService.recommandPainting(testData);
//     const isWorld = await pathGuideService.recommandWorld(testData);
//     const isCraft = await pathGuideService.recommandCraft(testData);

//     console.log(
//       isAncient,
//       isMedieval,
//       isMedieval,
//       isModern,
//       isDonation,
//       isPainting,
//       isWorld,
//       isCraft
//     );
//     //tf.tensor([1,2]).print();
//   }, 2000000);
// });



describe("Redis", () => {
  beforeAll(() => {
    sequelize;
    tf;
  });
  it("redis", async () => {
    const userLocationService = new UserLocationService();
    // await userLocationService.setUserLocation(23,2.1,3.6);
    // await userLocationService.setUserLocation(21,102,3.6);
    // await userLocationService.setUserLocation(22,32,20);
    const data = await userLocationService.getUserLocation(23);
    console.log(data);
  }, 2000000);
});
