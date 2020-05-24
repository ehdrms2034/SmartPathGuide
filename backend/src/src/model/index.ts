import { Sequelize } from "sequelize-typescript";
import { Member } from "@models/member";
import { Paths } from "@models/paths";
import { UserInfo } from "@models/UserInfo";
import { Place } from "@models/Place";

export const sequelize = new Sequelize({
  database: process.env.MYSQL_DATABASE,
  dialect: "mysql",
  username: "smartpath",
  password: "smartpath",
  host: process.env.MYSQL_HOST,
  logging: false,
});

sequelize.addModels([Member, Paths, UserInfo,Place]);

sequelize.sync();
