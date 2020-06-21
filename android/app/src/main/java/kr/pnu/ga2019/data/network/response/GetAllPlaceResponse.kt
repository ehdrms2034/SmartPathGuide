/*
 * Created by Lee Oh Hyoung on 2020/06/06 .. 
 */
package kr.pnu.ga2019.data.network.response

import com.google.gson.annotations.SerializedName
import kr.pnu.ga2019.data.network.Response
import kr.pnu.ga2019.domain.entity.Place

class GetAllPlaceResponse(

    @SerializedName("data")
    val data: List<PlaceResponse>
) : BaseResponse() {

    data class PlaceResponse(

        @SerializedName("id")
        val id: Int,

        @SerializedName("name")
        val name: String,

        @SerializedName("maxPeople")
        val maxPeople: Int,

        @SerializedName("locationX")
        val locationX: Int,

        @SerializedName("locationY")
        val locationY: Int,

        @SerializedName("createdAt")
        val createAt: String,

        @SerializedName("updateAt")
        val updateAt: String

    ): Response
}

fun GetAllPlaceResponse.toEntity(): List<Place> =
    data.map { response ->
        Place(
            id = response.id,
            name = response.name,
            maxPeople = response.maxPeople,
            locationX = response.locationX,
            locationY = response.locationY
        )
    }


fun GetAllPlaceResponse.PlaceResponse.toEntity(): Place =
    Place(
        id = id,
        name = name,
        maxPeople = maxPeople,
        locationX = locationX,
        locationY = locationY
    )
