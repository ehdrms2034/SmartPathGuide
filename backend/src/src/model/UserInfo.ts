import {Table,Column, PrimaryKey, ForeignKey} from 'sequelize-typescript';
import { Model } from 'sequelize/types';
import { Member } from './member';

@Table
class UserInfo extends Model<UserInfo> {

    @PrimaryKey
    @ForeignKey(()=>Member)
    @Column
    userSeq !: number;

    

}