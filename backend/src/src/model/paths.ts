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

    @Column
    public ancient !: number;

    @Column
    public medieval !: number;

    @Column
    public modern !: number;

    @Column
    public donation !: number;

    @Column
    public painting !: number;

    @Column
    public world !: number;

    @Column
    public craft !: number;
    
}