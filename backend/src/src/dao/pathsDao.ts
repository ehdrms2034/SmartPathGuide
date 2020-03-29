import {Paths} from '../model/paths';

export class PathsDao {

    /**
     * getPaths
     */
    public async getPaths(userPk : number) {
        return await Paths.findByPk(userPk);
    }

    public async savePaths(pathSeq : number, destination : string){
        const path = await Paths.findByPk(pathSeq);
        
        
    }

}