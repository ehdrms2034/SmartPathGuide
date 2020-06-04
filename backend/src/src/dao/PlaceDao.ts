import { Place } from "@models/Place";

class PlaceDao {
  constructor() {}

  async savePlace(
    name: string,
    locationX: number,
    locationY: number,
    maxPeople: number
  ) {
    const newPlace = new Place({
      name,
      locationX,
      locationY,
      maxPeople,
    });

    await newPlace.save();
  }

  async getPlaceByName(name: string) {
    return await Place.findOne({ where: { name } });
  }

  async getPlaces() {
    return await Place.findAll({ attributes: { exclude: ["key"] } });
  }
}

export default PlaceDao;
