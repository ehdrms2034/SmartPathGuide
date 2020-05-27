/*
 * Created by Lee Oh Hyoung on 2020/05/27 .. 
 */
package kr.pnu.ga2019.data.model.response

import com.google.gson.annotations.SerializedName
import kr.pnu.ga2019.data.model.Response

data class RecommendResponse(

    @SerializedName("data")
    val data: List<Path>
): Response() {

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
