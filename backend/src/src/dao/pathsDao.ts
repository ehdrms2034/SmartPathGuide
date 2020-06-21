import { Paths } from "../model/paths";
import { Member } from "src/model/member";
import { Model, where } from "sequelize/types";
import MemberDao from "./memberDao";
import { CustomError } from "@models/CustomError";
import { UserInfo } from '@models/UserInfo';

interface PathItem {
  seq: number;
  destination: string;
}
export interface RequestPath {
  sequence : number;
  placeId : number;
  stayTime : number;
};

export class PathsDao {
  /**
   * getPaths
   */
  public async getPaths(memberPk: number): Promise<Paths> {
    const memberDao = new MemberDao();
    return await memberDao.getPaths(memberPk);
  }

  public async getAllMemberPaths() {
    return await Paths.findAll();
  }

  public async getMemberPaths(memberPK : number){
    return await Paths.findAll({where : {userSeq : memberPK}});
  }

  public async createPaths(member: Member, RequestPath : RequestPath) {
    const newPath = Paths.build({
      userSeq: member.userSeq,
      sequence : RequestPath.sequence,
      placeId : RequestPath.placeId,
      stayTime : RequestPath.stayTime
    });
    return await newPath.save();
  }
}

export default PathsDao;
