import {Table, Column, Model, PrimaryKey, AutoIncrement, NotNull, HasMany, ForeignKey} from 'sequelize-typescript';
import { Paths} from './paths';

@Table
export class Member extends Model<Member>{
    
    @PrimaryKey
    @AutoIncrement
    @Column
    userSeq !: number;

    @Column
    age !: number;

    @HasMany(()=>Paths)
    paths !: Paths[];

}