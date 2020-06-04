import {Router} from 'express';
import { ApiResponse } from '@models/response';
import PlaceDao from '@daos/PlaceDao';

const router = Router();
const placeDao = new PlaceDao();

router.get("/places",async (req,res)=> {
    try{
        const places = await placeDao.getPlaces();
        return res.json(new ApiResponse("success","성공적으로 박물관 정보를 요청했습니다.",places));
    }catch(error){
        return res.json(new ApiResponse("error",error.getMessage,null));
    }
});


export default router;