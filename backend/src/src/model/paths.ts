import { ForeignKey, Column, Table, PrimaryKey, Model, AutoIncrement, BelongsTo, Unique, HasOne } from 'sequelize-typescript';
import { Member } from './member';
import { DataTypes } from 'sequelize';
import { Place } from './Place';

@Table
export class Paths extends Model<Paths> {

    @PrimaryKey
    @AutoIncrement
    @Column
    public pathSeq !: number;

    @ForeignKey(()=>Member)
    @Column
    public userSeq !: number;

    @BelongsTo(()=>Member)
    public member !: Member;

    @Column
    public sequence !: number;

    @Column
    public placeId !: number;

    @HasOne(()=>Place)
    public place !: Place;

    @Column
    public stayTime !: number;
}