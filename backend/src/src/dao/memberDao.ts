import {Member} from '@models/member';
import { CustomError } from '@models/CustomError';
import { Paths } from '@models/paths';

class MemberDao {
    
    constructor(){

    }

    public async saveMember(age : number){
        const newMember = Member.build({
            age,
        });
        return await newMember.save();
    }

    public async getAllMembers(){
            return await Member.findAll();
    }

    public async getMemberBySeq (seq : number) : Promise<Member>{
        const member = await Member.findByPk(seq);
        if(member==null) throw new CustomError("MemberDao.getMemberBySeq","조회하려는 회원정보가 존재하지 않습니다.");
        return member.toJSON() as Member;
    }

    public async getPaths (seq : number) : Promise<Paths>{
        const member = await Member.findOne({where : {userSeq : seq} , include : [Paths]});
        if(member==null) throw new CustomError("MemberDao.getPaths","조회하려는 회원정보가 존재하지 않습니다.");
        if(member.paths==null) throw new CustomError("MemberDao.getPaths","조회하려는 회원 path가 존재하지 않습니다.");
        return member.paths as Paths;
    }

}

 
export default MemberDao;