/*
 * Created by Lee Oh Hyoung on 2020/06/21 .. 
 */
package kr.pnu.ga2019.data.network.request

import androidx.annotation.IntRange
import com.google.gson.annotations.SerializedName
import kr.pnu.ga2019.data.network.Request

data class PathDataRequest(

    @SerializedName("pathData")
    val pathData: List<List<Any>>
): Request {

    data class PathDataRequest(
        val one: Int, // seq
        val two: Int, // placeId

        @IntRange(from = 0, to = 30)
        val three: Int
    )
}