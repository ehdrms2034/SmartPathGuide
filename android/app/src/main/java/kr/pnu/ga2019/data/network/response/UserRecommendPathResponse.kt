/*
 * Created by Lee Oh Hyoung on 2020/06/21 .. 
 */
package kr.pnu.ga2019.data.network.response

import com.google.gson.annotations.SerializedName
import kr.pnu.ga2019.domain.entity.PlaceInfo
import kr.pnu.ga2019.domain.entity.RecommendedPlace

data class UserRecommendPathResponse(

    @SerializedName("data")
    val data: List<RecommendPath>

): BaseResponse() {

    data class RecommendPath(

        @SerializedName("placeId")
        val placeId: Int,

        @SerializedName("name")
        val name: String,

        @SerializedName("percent")
        val percent: Double
    )
}

fun UserRecommendPathResponse.RecommendPath.toEntity() =
    PlaceInfo(placeId, name, percent)

fun UserRecommendPathResponse.toEntity(): RecommendedPlace =
    RecommendedPlace(
        first = data[0].toEntity(),
        second = data[1].toEntity(),
        third = data[2].toEntity()
    )