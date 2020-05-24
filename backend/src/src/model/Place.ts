import {Table ,Model, PrimaryKey, AutoIncrement, Column} from 'sequelize-typescript';

@Table
export class Place extends Model<Place> {

    @PrimaryKey
    @AutoIncrement
    @Column
    public key !: Number;

    @Column
    public name !: string;

    @Column
    public maxPeople !: number;

    @Column
    public locationX !: number;

    @Column
    public locationY !: number;
}