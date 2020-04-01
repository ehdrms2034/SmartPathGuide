import { ForeignKey, Column, Table, PrimaryKey, Model, AutoIncrement, BelongsTo, Unique } from 'sequelize-typescript';
import { Member } from './member';
import { DataTypes } from 'sequelize';

@Table
export class Paths extends Model<Paths> {

    @PrimaryKey
    @AutoIncrement
    @Column
    public pathSeq !: number;

    @ForeignKey(()=>Member)
    @Unique
    @Column
    public userSeq !: number;

    @BelongsTo(()=>Member)
    public member !: Member;

    @Column(DataTypes.JSON)
    public list !: any;

}