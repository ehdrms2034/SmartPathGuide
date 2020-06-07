import { Router, Request, Response } from "express";
import { PathsDao, RequestPath } from "@daos/pathsDao";
import { Member } from "src/model/member";
import { ApiResponse } from "@models/response";
import { CustomError } from "@models/CustomError";
import MemberDao from "@daos/memberDao";
import { INSUFFICIENT_SPACE_ON_RESOURCE } from "http-status-codes";

const router = Router();
const pathsDao = new PathsDao();

router.post("/", async (req: Request, res: Response) => {
  const {
    memberSeq,
    isAncient,
    isMedieval,
    isModern,
    isDonation,
    isPainting,
    isWorld,
    isCraft,
    isScience,
    isSpace,
    isHuman,
    isNatural,
    isFuture,
  } = req.body;
  const memberDao = new MemberDao();

  try {
    const member: Member = await memberDao.getMemberBySeq(memberSeq);
    const requestPath: RequestPath = {
      ancient: isAncient,
      medieval: isMedieval,
      modern: isModern,
      donation: isDonation,
      painting: isPainting,
      world: isWorld,
      craft: isCraft,
      science: isScience,
      space : isSpace,
      human : isHuman,
      natural: isNatural,
      future: isFuture
    };
    const newPaths = await pathsDao.createPaths(member, requestPath);

    res.json(
      new ApiResponse(
        "success",
        "성공적으로 유저의 방문경로를 저장했습니다.",
        null
      )
    );
    return;
  } catch (error) {
    if (error instanceof CustomError) {
      res.json(new ApiResponse("error", error.getMessage, null));
      return;
    }
    res.json(new ApiResponse("error", error, null));
  }
});
export default router;
