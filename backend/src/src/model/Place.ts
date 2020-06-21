import {Table ,Model, PrimaryKey, AutoIncrement, Column, ForeignKey} from 'sequelize-typescript';
import { Paths } from './paths';

@Table
export class Place extends Model<Place> {

    @PrimaryKey
    @AutoIncrement
    @Column
    @ForeignKey(()=>Paths)
    public id !: Number;

    @Column
    public name !: string;

    @Column
    public maxPeople !: number;

    @Column
    public locationX !: number;

    @Column
    public locationY !: number;
}