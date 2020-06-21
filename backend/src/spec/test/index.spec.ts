import { expect } from "chai";
import { sequelize } from "@models/index";
import UserInfoDao from "@daos/UserInfoDao";
import MemberDao from "@daos/memberDao";
import PathsDao, { RequestPath } from "@daos/pathsDao";
import { UserInfo } from "@models/UserInfo";
import { ENUM } from "sequelize/types";
import redisClient, { redisSetData, redisGetData } from "@modules/redis";
import { Paths } from "@models/paths";
import * as tf from "@tensorflow/tfjs-node";
import {reshape} from "@tensorflow/tfjs-node";
import UserLocationService from "@services/UserLocationService";
import PlaceDao from "@daos/PlaceDao";
import PathGenerator from "@modules/PathGenerator";
import { math } from "@tensorflow/tfjs-node";
import PathRecommendService from "@services/PathRecommendService";
import { userInfo } from "os";
import { Districts } from "@models/Districts";

// describe("DB세팅", () => {
//   beforeAll(() => {
//     sequelize;
//     redisClient;
//   });
//   it("test", async () => {

//     const PathRecommendService = new PathRecommendService();
//     const infoList = await PathRecommendService.getAllUserInfo();
//     const pathList = await PathRecommendService.findPathsByItemBase(15,infoList);
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
//       newUserInfo.science = Math.round(Math.random() * 100) + 1;
//       newUserInfo.space = Math.round(Math.random() * 100) + 1;
//       newUserInfo.human = Math.round(Math.random() * 100) + 1;
//       newUserInfo.natural = Math.round(Math.random() * 100) + 1;
//       newUserInfo.future = Math.round(Math.random() * 100) + 1;
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
//       const isScience = Math.round(Math.random()*100 / newUserInfo.science) <= 1 ? 1 : 0;
//       const isSpace = Math.round(Math.random()*100 / newUserInfo.space) <= 1 ? 1 : 0;
//       const isHuman = Math.round(Math.random()*100 / newUserInfo.human) <= 1 ? 1 : 0;
//       const isNatural = Math.round(Math.random()*100 / newUserInfo.natural) <= 1 ? 1 : 0;
//       const isFuture = Math.round(Math.random()*100 / newUserInfo.future) <= 1 ? 1 : 0;

//       const reqPaths :RequestPath = {
//         ancient : isAncient,
//         medieval : isMedieval,
//         modern : isModern,
//         donation : isDonation,
//         painting : isPainting,
//         world : isWorld,
//         craft : isCraft,
//         science : isScience,
//         space : isSpace,
//         human : isHuman,
//         natural : isNatural,
//         future : isFuture
//       };

//       await pathsDao.createPaths(member,reqPaths);
//       await pathsDao.setPaths(member.userSeq,reqPaths);
//     }
//   },2000000);
// });

// describe("DB Setting 2", () => {
//   beforeAll(() => {
//     sequelize;
//     tf;
//   });
//   it("DB", async () => {
//     const memberDao = new MemberDao();
//     const pathDao = new PathsDao();
//     const placeDao = new PlaceDao();
//     const userInfoDao = new UserInfoDao();
//     const pathGenerator = new PathGenerator();
//     for (let j = 0; j < 20000; j++) {
//       const member = await memberDao.saveMember(Math.floor(Math.random() * 90));
//       const path = pathGenerator.getPath();
//       for (let i = 0; i < path.length; i++) {
//         const requestPath: RequestPath = {
//           sequence: i,
//           placeId: path[i].place,
//           stayTime: path[i].stayTime,
//         };
//         await pathDao.createPaths(member,requestPath);
//       }
//       await userInfoDao.createUserInfo(member.userSeq);
//       const pathData = new UserInfo();

//       for (let i = 0; i < path.length; i++) {
//         if (path[i].place === Districts.ANCIENT)
//           pathData.ancient = path[i].stayTime;
//         if (path[i].place === Districts.MEDIEVAL)
//           pathData.medieval = path[i].stayTime;
//         if (path[i].place === Districts.MODERN)
//           pathData.modern = path[i].stayTime;
//         if (path[i].place === Districts.DONATION)
//           pathData.donation = path[i].stayTime;
//         if (path[i].place === Districts.PAINTING)
//           pathData.painting = path[i].stayTime;
//         if (path[i].place === Districts.WORLD)
//           pathData.world = path[i].stayTime;
//         if (path[i].place === Districts.CRAFT)
//           pathData.craft = path[i].stayTime;
//         if (path[i].place === Districts.SCIENCE)
//           pathData.science = path[i].stayTime;
//         if (path[i].place === Districts.SPACE)
//           pathData.space = path[i].stayTime;
//         if (path[i].place === Districts.HUMAN)
//           pathData.human = path[i].stayTime;
//         if (path[i].place === Districts.NATURAL)
//           pathData.natural = path[i].stayTime;
//         if (path[i].place === Districts.FUTURE)
//           pathData.future = path[i].stayTime;
//       }
//       await userInfoDao.setUserInfo(member.userSeq, pathData);
//     }
//   }, 2000000);
// });

describe("Tensorflow",()=>{
  beforeAll(()=>{
    sequelize;
    tf;
  });
  it("tesorTuto",async ()=>{
    const pathRecommendService = new PathRecommendService();
    // const data = await pathRecommendService.getTrainSet();
    const testData = await pathRecommendService.getTestSet();
    // console.log(testData[0].length,testData[1].length);
    // await pathRecommendService.makeRecommendModel();
    const predictData = testData[0][3];
    const data = reshape(predictData,[1,12,2]);
    data.print();
    const predict=await pathRecommendService.predictPaths(data);
    console.log(predict);
  },2000000);
})

// describe("Tensorflow",()=>{
//   beforeAll(()=>{
//     sequelize;
//     tf;
//   });
//   it("lstm",async ()=>{
//     const pathRecommendService = new PathRecommendService();
//     await pathRecommendService.makeRecommendModel();

//     //tf.tensor([1,2]).print();
//   },2000000);
// })

// describe("Tensorflow", () => {
//   beforeAll(() => {
//     sequelize;
//     tf;
//   });
//   it("tesorTuto", async () => {
//     const PathRecommendService = new PathRecommendService();
//     const testData = tf.tensor([[30, 30, 60, 80, 80, 40, 70]]);
//     const isAncient = await PathRecommendService.recommandAntient(testData);
//     const isMedieval = await PathRecommendService.recommandMedieval(testData);
//     const isModern = await PathRecommendService.recommandModern(testData);
//     const isDonation = await PathRecommendService.recommandDonation(testData);
//     const isPainting = await PathRecommendService.recommandPainting(testData);
//     const isWorld = await PathRecommendService.recommandWorld(testData);
//     const isCraft = await PathRecommendService.recommandCraft(testData);

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

// describe("Redis", () => {
//   beforeAll(() => {
//     sequelize;
//     tf;
//   });
//   it("redis", async () => {
//     const userLocationService = new UserLocationService();
//     await userLocationService.setUserLocation(23,2.1,3.6);
//     await userLocationService.setUserLocation(21,102,3.6);
//     await userLocationService.setUserLocation(22,32,20);
//     const data = await userLocationService.getUserLocation(23);
//     console.log(data);
//   }, 2000000);
// });

// describe("placeSave", () => {
//   beforeAll(() => {
//     sequelize;
//     tf;
//   });
//   it("placeSave", async () => {
//     const placeDao = new PlaceDao();
//     await placeDao.savePlace("ancient",100,50,30);
//     await placeDao.savePlace("medieval",600,50,50);
//     await placeDao.savePlace("modern",1000,50,50);
//     await placeDao.savePlace("donation",450,300,60);
//     await placeDao.savePlace("painting",800,300,40);
//     await placeDao.savePlace("world",300,600,40);
//     await placeDao.savePlace("craft",900,600,50);

//   }, 2000000);
// });

// describe("recommend", async () => {
//   beforeAll(() => {
//     sequelize;
//     tf;
//   });

//   it("test", async () => {
//     const pathGuideService = new PathGuideService();
//     const userLocationService = new UserLocationService();
//     try {
//       await userLocationService.setUserLocation(23, 600, 200);
//       const list = await pathGuideService.guidePathsByUserSeq(23);
//       console.log(list);
//     } catch (error) {
//       if (error instanceof CustomError) console.log(error.getMessage);
//       else console.log(error);
//     }
//   },2000000);

// });
