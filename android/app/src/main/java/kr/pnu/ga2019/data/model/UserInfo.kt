/*
 * Created by Lee Oh Hyoung on 2020/05/27 .. 
 */
package kr.pnu.ga2019.data.model

import com.google.gson.annotations.SerializedName

data class UserInfo(

    @SerializedName("memberSeq")
    val memberSeq: Int,

    @SerializedName("locationX")
    val locationX: Double,

    @SerializedName("locationY")
    val locationY: Double
)
