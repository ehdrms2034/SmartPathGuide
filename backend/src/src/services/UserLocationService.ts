import { redisSetData, redisGetData } from "@modules/redis";
import { CustomError } from "@models/CustomError";

export interface UserLocation {
  memberSeq: number;
  locationX: number;
  locationY: number;
}

class UserLocationService {
  static currentUsers: Array<UserLocation> = [];

  constructor() {}

  async setUserLocation(id: number, locationX: number, locationY: number) {
    const userLocation: UserLocation = {
      memberSeq: id,
      locationX,
      locationY,
    };

    const { currentUsers } = UserLocationService;
    const index = currentUsers.findIndex((it) => it.memberSeq === id);
    if (index !== -1) currentUsers.splice(index, 1);
    currentUsers.push(userLocation);
    await redisSetData(
      id.toString(),
      JSON.stringify({ memberSeq: id, locationX, locationY })
    );
  }

  async getUserLocation(id: number) {
    const data = (await redisGetData(id.toString())) as string;
    if (data == null)
      throw new CustomError("error", "내 위치를 조회할 수 없습니다.");
    const { currentUsers } = UserLocationService;
    const myLocation: UserLocation = JSON.parse(data);
    const result = [];
    const usersData = [];

    result.push({ mydata: myLocation });

    for (const userLocation of currentUsers) {
      const data = (await redisGetData(
        userLocation.memberSeq.toString()
      )) as string;
      if (data == null) {
        const index = currentUsers.indexOf(userLocation);
        currentUsers.splice(index, 1);
        continue;
      }
      const userData: UserLocation = JSON.parse(data);
      if (userData.memberSeq !== id) usersData.push(userData);
    }
    result.push({ usersData: usersData });
    return result;
  }
}

export default UserLocationService;
