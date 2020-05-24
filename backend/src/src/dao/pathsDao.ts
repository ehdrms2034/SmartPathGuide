import { Paths } from "../model/paths";
import { Member } from "src/model/member";
import { Model } from "sequelize/types";
import MemberDao from "./memberDao";
import { CustomError } from "@models/CustomError";
import { UserInfo } from '@models/UserInfo';

interface PathItem {
  seq: number;
  destination: string;
}
export interface RequestPath {
  ancient : number,
  medieval : number,
  modern : number,
  donation : number,
  painting : number,
  world : number,
  craft : number
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

  public async createPaths(member: Member, RequestPath : RequestPath) {
    const newPath = Paths.build({
      userSeq: member.userSeq,
    });
    return await newPath.save();
  }

  public async setPaths(memberPK: number, reqPath : RequestPath) {
    const paths = await this.getPaths(memberPK);
    paths.ancient = reqPath.ancient;
    paths.medieval = reqPath.medieval;
    paths.modern = reqPath.modern;
    paths.donation = reqPath.donation;
    paths.painting = reqPath.painting;
    paths.world = reqPath.world;
    paths.craft = reqPath.craft;
    return await paths.save();
  }

  // public async isInDestination(memberPK: number, destination: string) {
  //   const paths = await this.getPaths(memberPK);
  //   let list: Array<PathItem> = paths.list;
  //   try {
  //     list.forEach((it) => {
  //       if (it.destination === destination)
  //         throw new CustomError("error", "this destination is already in here");
  //     });
  //   } catch (e) {
  //     return true;
  //   }
  //   return false;
  // }

}

export default PathsDao;
