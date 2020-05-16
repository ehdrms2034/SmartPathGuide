import * as redis from 'redis';
import { resolve, reject } from 'bluebird';

const redisClient = new redis.RedisClient({host : "localhost",port:6379});

export const redisSetData = async (key: string,value : any) => {
    return new Promise((resolve,reject)=>{
        redisClient.setex(key,60*2,value,(err,reply)=>{
            if(err) reject(err);
            return resolve(reply);
        });
    })
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