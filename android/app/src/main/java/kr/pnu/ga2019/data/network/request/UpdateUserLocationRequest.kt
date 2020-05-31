/*
 * Created by Lee Oh Hyoung on 2020/05/28 .. 
 */
package kr.pnu.ga2019.data.network.request

import com.google.gson.annotations.SerializedName
import kr.pnu.ga2019.data.network.Request

data class UpdateUserLocationRequest(

    @SerializedName("memberSeq")
    val memberSeq: Int,

    @SerializedName("locationX")
    val locationX: Int,

    @SerializedName("locationY")
    val locationY: Int
) : Request
