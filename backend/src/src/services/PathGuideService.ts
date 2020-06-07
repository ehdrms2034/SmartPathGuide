import UserLocationService from "./UserLocationService";
import PlaceService from "./PlaceService";
import { CustomError } from "@models/CustomError";
import PathRecommendService from "./PathRecommendService";
import UserInfoDao from "@daos/UserInfoDao";
import * as tf from "@tensorflow/tfjs-node";
import Graph from "node-dijkstra";
import { Place } from "@models/Place";
import { math } from "@tensorflow/tfjs-node";

class PathGuideService {
  private userLocationService: UserLocationService;
  private placeService: PlaceService;
  private pathRecommendService: PathRecommendService;
  private userInfoDao: UserInfoDao;

  constructor() {
    this.userLocationService = new UserLocationService();
    this.placeService = new PlaceService();
    this.pathRecommendService = new PathRecommendService();
    this.userInfoDao = new UserInfoDao();
  }

  async guidePathsByUserSeq(memberPK: number) {
    const {
      userLocationService,
      placeService,
      userInfoDao,
      pathRecommendService,
    } = this;
    const userLocation = await userLocationService.getUserLocation(memberPK);
    const myLocation = userLocation.mydata;
    const userInfo = await userInfoDao.getUserInfo(memberPK);
    if (myLocation === null || myLocation === undefined)
      throw new CustomError(
        "PathGuideService : guidePathByMemberSeq",
        "현재 유저의 위치가 설정되어있지 않습니다."
      );
    const userTensor = tf.tensor([
      [
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
        userInfo.future
      ],
    ]);
    const isAncient = await pathRecommendService.recommandAntient(userTensor);
    const isMedieval = await pathRecommendService.recommandMedieval(userTensor);
    const isModern = await pathRecommendService.recommandModern(userTensor);
    const isDonation = await pathRecommendService.recommandDonation(userTensor);
    const isPainting = await pathRecommendService.recommandPainting(userTensor);
    const isWorld = await pathRecommendService.recommandPainting(userTensor);
    const isCraft = await pathRecommendService.recommandCraft(userTensor);
    const isScience = await pathRecommendService.recommandScience(userTensor);
    const isSpace = await pathRecommendService.recommandSpace(userTensor);
    const isHuman = await pathRecommendService.recommandHuman(userTensor);
    const isNatural = await pathRecommendService.recommandNatural(userTensor);
    const isFuture = await pathRecommendService.recommandFuture(userTensor);

    const recommendResult = [
      isAncient,
      isMedieval,
      isModern,
      isDonation,
      isPainting,
      isWorld,
      isCraft,
      isScience,
      isSpace,
      isHuman,
      isNatural,
      isFuture
    ];

    const recommends = recommendResult
      .filter((it) => it === true)
      .map((value, index) => {
        switch (index) {
          case 0:
            return "ancient";
          case 1:
            return "medieval";
          case 2:
            return "modern";
          case 3:
            return "donation";
          case 4:
            return "painting";
          case 5:
            return "world";
          case 6:
            return "craft";
          case 7:
            return "science"
          case 8:
            return "space"
          case 9:
            return "human"
          case 10:
            return "natural"
          case 11:
            return "future"
        }
      }) as Array<string>;

    // console.log(recommends);
    if (recommends.length === 0)
      throw new CustomError(
        "PathGuideService : guidePathByMemberSeq",
        "추천 대상이 존재하지 않습니다."
      );

    let places: Array<Place> = [];
    const myTmpLocation = new Place({
      name: "Entrance/Exit",
      locationX: myLocation.locationX,
      locationY: myLocation.locationY,
      maxPeople: 0,
    });
    places.push(myTmpLocation);
    const data = await placeService.getPlaceByArray(recommends);
    places = places.concat(data);

    const visited = Array(places.length).fill(false);
    let currentVisit = 0;
    let totalVisit = 1;

    const parsedList = [];
    parsedList.push(myTmpLocation);
    while (totalVisit < places.length) {
      if (places === null)
        throw new CustomError(
          "PathGuideService : guidePathByMemberSeq",
          "추천 대상이 존재하지 않습니다."
        );
      visited[currentVisit] = true;
      const lists = places
        .filter((value, index) => visited[index] === false)
        .map((value, index) => {
          const data = value as Place;
          const distance =
            Math.abs(places[currentVisit].locationX - data.locationX) +
            Math.abs(places[currentVisit].locationY - data.locationY);

          return { name: data.name, distance: distance };
        });
      let minDistance = 999999999;
      let nextDestination = "";
      for (const place of lists) {
        if (minDistance > place.distance) {
          minDistance = place.distance;
          nextDestination = place.name;
        }
      }
      let nextVisit = places.findIndex((it) => it.name === nextDestination);
      currentVisit = nextVisit;
      totalVisit++;
      parsedList.push(places[currentVisit]);
    }
    parsedList.push(myTmpLocation);
    const resultData = parsedList.map((it,index)=>{return {seq : index ,name : it.name, locationX : it.locationX, locationY : it.locationY}});
    return resultData;
  }
}

export default PathGuideService;
