import { Request, Response, Router } from "express";
import { ApiResponse } from "@models/response";

import MemberDao from "@daos/memberDao";

// Init shared
const router = Router();
const memberDao = new MemberDao();

router.get("/", async (req: Request, res: Response) => {});

router.get("/findAllMembers", async (req: Request, res: Response) => {
  const data = await memberDao.getAllMembers();
  return res.json(data);
});

router.post("/createMember", async (req: Request, res: Response) => {
  const { age } = req.body;
  try {
    await memberDao.saveMember(age);
    const response = new ApiResponse(
      "success",
      "회원추가에 성공했습니다.",
      null
    );
    return res.json(response);
  } catch (error) {
    res.json(new ApiResponse("error","회원추가에 실패했습니다.",error))
  }
});

export default router;
