import UserInfoDao from '@daos/UserInfoDao';
import { Member } from '@models/member';


class PathGuiderService {

    private userInfoDao : UserInfoDao;

    constructor(){
        this.userInfoDao = new UserInfoDao();
    }
    

    public async getAllUserInfo() {
        const {userInfoDao } = this;
        let paths = await userInfoDao.getAllUserInfo();
        return paths;
    }


    public async findPathsByCosine(memberPK : number){
        const {userInfoDao} = this;
        let paths = await this.getAllUserInfo();
        const userInfo = await userInfoDao.getUserInfo(memberPK)
        
    }
    

};

export default PathGuiderService;