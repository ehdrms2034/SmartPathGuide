import * as redis from 'redis';
import { resolve, reject } from 'bluebird';

const redisClient = new redis.RedisClient({host : "localhost",port:6379});

export const redisSetData = (key: string,value : any) => {
    redisClient.set(key,value);
}

export const redisGetData = (key : string) => {
    return new Promise((resolve,reject)=>{
        redisClient.get(key,(err,reply)=>{
            if(err) reject(err)
            return resolve(reply)
        });
    });
}

export default redisClient;