import { expect } from "chai";
import {sequelize} from '@models/index';
import MemberDao from '@daos/memberDao';


describe('index.ts 테스트',()=>{

    beforeAll(()=>{
        sequelize;
    })
    it('test',async ()=>{
        const dao = new MemberDao();
        const data = await dao.getMemberBySeq(2);
        console.log(data);
    })
});