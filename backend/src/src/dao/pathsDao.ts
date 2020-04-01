import {Paths} from '../model/paths';
import { Member } from 'src/model/member';
import { Model } from 'sequelize/types';
import MemberDao from './memberDao';

export class PathsDao {

    /**
     * getPaths
     */
    public async getPaths(memberPk : number) : Promise<Paths> {
        const memberDao = new MemberDao();
        return await memberDao.getPaths(memberPk);
    }

    public async createPaths(member : Member, destination : string){
        const newPath = Paths.build({
            userSeq : member.userSeq,
            paths:[{
                seq : 1,
                destination : destination
            }]
        });
        return await newPath.save();
    }

    public async addPaths(memberPK : number, destination : string){
        const paths = await this.getPaths(memberPK);
        let list : Array<Object> = paths.list;
        list.push({
            seq : list.length +1,
            destination
        });
        paths.list = list;
        await paths.save();
        return paths;
    }

}

export default PathsDao;