import { redisSetData, redisGetData } from '@modules/redis';

class UserLocationService {

    constructor() {

    }

    setUserLocation(id:number,locationX:number,locationY:number){
        redisSetData(id.toString(),JSON.stringify({locationX,locationY}))
    }

    async getUserLocation(id:number){
        const data = (await redisGetData(id.toString())) as string;
        return JSON.parse(data);
    }
};

export default UserLocationService;