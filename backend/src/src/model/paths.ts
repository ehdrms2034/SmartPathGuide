import { ForeignKey, Column, Table, PrimaryKey, Model, AutoIncrement, BelongsTo } from 'sequelize-typescript';
import { Member } from './member';
import { DataTypes } from 'sequelize';

@Table
export class Paths extends Model<Paths> {

    @PrimaryKey
    @ForeignKey(()=>Member)
    @AutoIncrement
    @Column
    public pathSeq !: number;

    @BelongsTo(()=>Member)
    public member !: Member;

    @Column(DataTypes.JSON)
    public paths !: any;

}