/*
 * Created by Lee Oh Hyoung on 2020/05/27 .. 
 */
package kr.pnu.ga2019.data.network.response

import com.google.gson.annotations.SerializedName
import kr.pnu.ga2019.domain.entity.Point

data class RecommendResponse(

    @SerializedName("data")
    val data: List<Path>?
): BaseResponse() {

    data class Path(

        @SerializedName("seq")
        val seq: Int,

        @SerializedName("name")
        val name: String,

        @SerializedName("locationX")
        val locationX: Double,

        @SerializedName("locationY")
        val locationY: Double
    )
}


fun RecommendResponse.Path.toEntity(): Point =
    Point(
        seq = seq,
        name = name,
        locationX = locationX,
        locationY = locationY
    )

fun RecommendResponse.toEntity(): List<Point> =
    data?.map { path ->
        with(path) {
            Point(
                seq = seq,
                name = name,
                locationX = locationX,
                locationY = locationY
            )
        }
    } ?: emptyList()
