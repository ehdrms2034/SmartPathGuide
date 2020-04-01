import { Router, Request, Response } from "express";
import { PathsDao } from "@daos/pathsDao";
import { Member } from "src/model/member";
import { ApiResponse } from "@models/response";
import { CustomError } from "@models/customError";
import MemberDao from "@daos/memberDao";

const router = Router();
const pathsDao = new PathsDao();

router.post("/createPath", async (req: Request, res: Response) => {
  const { memberSeq, destination } = req.body;
  const memberDao = new MemberDao();

  try {
    const member: Member = await memberDao.getMemberBySeq(memberSeq);
    const newPaths = await pathsDao.createPaths(member, destination);
    res.json(newPaths);
    return;
  } catch (error) {
    if (error instanceof CustomError) {
      res.json(new ApiResponse("error", error.getMessage, null));
      return;
    }
    res.json(new ApiResponse("error", error, null));
  }
});

router.post("/addPath", async (req: Request, res: Response) => {
  const { memberSeq, destination } = req.body;
  try{
    await pathsDao.addPaths(memberSeq, destination);
    res.json(new ApiResponse("success","성공적으로 경로가 추가되었습니다.",""));
}catch(error){
    if(error instanceof CustomError){
        res.json(new ApiResponse("error",error.getMessage,null));
        return;
    }


  }
});

export default router;