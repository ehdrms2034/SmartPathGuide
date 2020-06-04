import { Sequelize } from "sequelize-typescript";
import { Member } from "@models/member";
import { Paths } from "@models/paths";
import { UserInfo } from "@models/UserInfo";
import { Place } from "@models/Place";

export const sequelize = new Sequelize({
  database: "smartpath",
  dialect: "mysql",
  username: "smartpath",
  password: "smartpath",
  host: "localhost",
  logging: false,
});

sequelize.addModels([Member, Paths, UserInfo,Place]);

sequelize.sync();
