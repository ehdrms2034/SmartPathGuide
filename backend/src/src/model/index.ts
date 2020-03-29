import {Sequelize} from 'sequelize-typescript';
import { Member } from './member';
import { Paths } from './paths';

export const sequelize = new Sequelize({
    database : process.env.MYSQL_DATABASE,
    dialect : 'mysql',
    username : "ehdrms2034",
    password : "kk940501",
    host : process.env.MYSQL_HOST
});

sequelize.addModels([Member,Paths]);

sequelize.sync();