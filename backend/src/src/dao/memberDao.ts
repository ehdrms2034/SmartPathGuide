import {Member} from '../model/member';

class MemberDao {
    
    constructor(){

    }

    /**
     * getMemberBySeq
    */

    public async saveMember(age : number){
        const newMember = Member.build({
            age
        });
        await newMember.save();
    }

    public async getAllMembers(){
        return await Member.findAll();
        
    }

    public async getMemberBySeq(seq : number){

        return await Member.findByPk(seq)
    }

}

const memberDao = new MemberDao();
 
export default memberDao;