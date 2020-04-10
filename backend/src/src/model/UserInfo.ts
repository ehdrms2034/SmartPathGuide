import {Table,Column, PrimaryKey, ForeignKey,Model,BelongsTo, Default} from 'sequelize-typescript';
import { Member } from '@models/member';

@Table
export class UserInfo extends Model<UserInfo> {

    @PrimaryKey
    @ForeignKey(()=>Member)
    @Column
    userSeq !: number;

    @Default(0)
    @Column
    ancient !: number;

    @Default(0)
    @Column 
    medieval !: number;

    @Default(0)
    @Column
    modern !: number;

    @Default(0)
    @Column
    donation !: number;

    @Default(0)
    @Column
    painting !: number;

    @Default(0)
    @Column
    world !: number;
    
    @Default(0)
    @Column
    craft !:number;

    @BelongsTo(()=>Member)
    member !:Member;
}