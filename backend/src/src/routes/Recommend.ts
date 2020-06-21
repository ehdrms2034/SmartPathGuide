import { Router} from "express";
import { CustomError } from "@models/CustomError";
import { ApiResponse } from "@models/response";
import PathRecommendService from '@services/PathRecommendService';
import * as tf from '@tensorflow/tfjs-node';
import { reshape } from '@tensorflow/tfjs-node';

const router = Router();

router.post("/", async (req, res) => {
  let { pathData } = req.body;
  console.log(pathData);
  const pathGuideService = new PathRecommendService();
  try {
    const newData = (pathData as Array<any>).map(it=>{return [it[1],it[2]]})
    const tensor = reshape(newData,[1,12,2]);
    let list = await pathGuideService.predictPaths(tensor);
    list = list.slice(0,3);
    res.json(
      new ApiResponse("success", "성공적으로 경로를 추천했습니다.", list)
    );
  } catch (error) {
    let message;
    if (error instanceof CustomError)
      message = `${error.getType} : ${error.getMessage}`;
    else message = error.message;
    res.json(new ApiResponse("error", message, null));
  }
});

export default router;
