import e, { Router, query } from "express";
import { CustomError } from "@models/customError";
import PathGuideService from "@services/PathGuideService";
import { ApiResponse } from "@models/response";
const router = Router();

router.get("/:memberPK", async (req, res) => {
  const { memberPK } = req.params;
  const queryPk = memberPK as string;
  const pathGuideService = new PathGuideService();
  try {
    if (memberPK === undefined)
      throw new CustomError("recommend", "memberPK error!");
    const pk = parseInt(queryPk);
    const list = await pathGuideService.guidePathsByUserSeq(pk);
    res.json(
      new ApiResponse("success", "성공적으로 경로를 추천했습니다.", list)
    );
  } catch (error) {
    let message;
    if (error instanceof CustomError)
      message = `${error.getType} : ${error.getMessage}`;
    else message = error.message;
    res.json(new ApiResponse("error", "경로를 추천할 수 없었습니다.", message));
  }
});

export default router;
