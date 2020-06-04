import PlaceDao from '@daos/PlaceDao';
import { Place } from '@models/Place';
import { CustomError } from '@models/CustomError';


class PlaceService {
    
    private placeDao : PlaceDao
    
    constructor(){
        this.placeDao = new PlaceDao();
    }

    async savePlace(name : string, locationX : number, locationY : number, maxPeople : number){
        const {placeDao} = this;
        await placeDao.savePlace(name,locationX,locationY,maxPeople);
    }

    async getPlaceByName(name : string){
        const {placeDao} = this;
        return placeDao.getPlaceByName(name);
    }

    async getPlaces(){
        return await this.placeDao.getPlaces()
    }

    async getPlaceByArray(names : Array<string>) : Promise<Array<Place>>{
        const places : Array<Place> = [];
        for(const name of names){
          const data = await this.getPlaceByName(name);
          if(data === null) throw new CustomError("placeService, getPlaceByName","data인자 찾을 수 없음");
          places.push(data);
        }
        return places;
      }


}
export default PlaceService;