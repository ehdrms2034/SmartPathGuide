import {Router,Request,Response} from 'express';
import {UserInfoDao} from '@daos/UserInfoDao'
import { ApiResponse } from '@models/response';
import { UserInfo } from '@models/UserInfo';

const router = Router();

router.post('/createUserInfo',async (req : Request, res : Response)=>{
    const {memberSeq} = req.body;
    const userInfoDao = new UserInfoDao();
    try{
        await userInfoDao.createUserInfo(memberSeq);
        res.json(new ApiResponse("success","userInfo데이터 생성을 성공했습니다.",null));
    }catch(e){
        if(e instanceof ApiResponse){
            res.json(new ApiResponse("error",e.getMessage,null));
        }
    }
});

router.post('/setUserInfo',async (req : Request, res : Response)=>{
    const {memberSeq, ancient, medieval, modern,
    donation, painting, world, craft} = req.body;
    
    const dummyUserInfo = new UserInfo();
    dummyUserInfo.ancient = ancient;
    dummyUserInfo.medieval = medieval;
    dummyUserInfo.modern = modern;
    dummyUserInfo.donation = donation;
    dummyUserInfo.painting = painting;
    dummyUserInfo.world = world;
    dummyUserInfo.craft = craft;

    const userInfoDao = new UserInfoDao();
    try{
        await userInfoDao.setUserInfo(1,memberSeq);
        res.json(new ApiResponse("success","유저 Info를 성공적으로 수정했습니다.",null));
    }catch(e){
        if(e instanceof ApiResponse){
            res.json(new ApiResponse("error",e.getMessage,null));
        }
    }
});

export default router;