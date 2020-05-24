import { Request, Response, Router } from "express";
import { ApiResponse } from "@models/response";

import MemberDao from "@daos/memberDao";
import UserInfoDao from "@daos/UserInfoDao";
import { UserInfo } from '@models/UserInfo';

// Init shared
const router = Router();
const memberDao = new MemberDao();
const userInfoDao = new UserInfoDao();

router.get("/", async (req: Request, res: Response) => {});

router.get("/members", async (req: Request, res: Response) => {
  const data = await memberDao.getAllMembers();
  return res.json(data);
});

router.post("/member", async (req: Request, res: Response) => {
  const { age, ancient, medieval, modern,
    donation,painting,world,craft } = req.body;
  try {
    const newUser = await memberDao.saveMember(age);
    const requestInfo : UserInfo = new UserInfo({
      ancient,medieval,modern,donation,painting,world,craft
    });
    await userInfoDao.createUserInfo(newUser.userSeq);
    await userInfoDao.setUserInfo(newUser.userSeq,requestInfo);
    const response = new ApiResponse(
      "success",
      "회원추가에 성공했습니다.",
      {memberPK : newUser.userSeq}
    );
    return res.json(response);
  } catch (error) {
    res.json(new ApiResponse("error","회원추가에 실패했습니다.",error))
  }
});

export default router;
