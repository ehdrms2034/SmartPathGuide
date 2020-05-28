/*
 * Created by Lee Oh Hyoung on 2020/05/27 .. 
 */
package kr.pnu.ga2019.data.model.response

import com.google.gson.annotations.SerializedName
import kr.pnu.ga2019.data.model.Response
import kr.pnu.ga2019.domain.entity.User

data class UserInfo(

    @SerializedName("memberSeq")
    val memberSeq: Int,

    @SerializedName("locationX")
    val locationX: Double,

    @SerializedName("locationY")
    val locationY: Double
): Response

fun UserInfo.toEntity(): User =
    User(
        id = memberSeq,
        locationX = locationX,
        locationY = locationY
    )
