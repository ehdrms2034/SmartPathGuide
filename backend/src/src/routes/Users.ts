import { Request, Response, Router } from 'express';
import { BAD_REQUEST, CREATED, OK } from 'http-status-codes';
import { ParamsDictionary } from 'express-serve-static-core';

import {Member} from '../model/member';
import memberDao from '../dao/memberDao';

// Init shared
const router = Router();

router.get("/", async(req: Request, res:Response)=>{
    

});

router.get("/findAllMembers",async(req : Request, res: Response)=>{
    const data = await memberDao.getAllMembers();
    return res.json(data);
});

router.post("/createMember", async(req : Request, res : Response)=>{
    const {age} = req.body;
    try{
        await memberDao.saveMember(age);
        
    }catch(error){
        
    }


});


export default router;
