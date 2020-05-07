import { Paths } from "../model/paths";
import { Member } from "src/model/member";
import { Model } from "sequelize/types";
import MemberDao from "./memberDao";
import { CustomError } from "@models/customError";

interface PathItem {
  seq: number;
  destination: string;
}

export class PathsDao {
  /**
   * getPaths
   */
  public async getPaths(memberPk: number): Promise<Paths> {
    const memberDao = new MemberDao();
    return await memberDao.getPaths(memberPk);
  }

  public async createPaths(member: Member) {
    const newPath = Paths.build({
      userSeq: member.userSeq,
      list: [],
    });
    return await newPath.save();
  }

  public async addPaths(memberPK: number, destination: string) {
    const paths = await this.getPaths(memberPK);
    console.log("paths:::", paths.list);
    let list: Array<Object> = paths.list;
    list.push({
      seq: list.length + 1,
      destination,
    });
    paths.list = list;
    await paths.save();
    return paths;
  }

  public async isInDestination(memberPK: number, destination: string) {
    const paths = await this.getPaths(memberPK);
    let list: Array<PathItem> = paths.list;
    try {
      list.forEach((it) => {
        if (it.destination === destination)
          throw new CustomError("error", "this destination is already in here");
      });
    } catch (e) {
      return true;
    }
    return false;
  }
}

export default PathsDao;
