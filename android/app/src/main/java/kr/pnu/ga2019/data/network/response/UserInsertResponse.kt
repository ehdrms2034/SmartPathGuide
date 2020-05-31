/*
 * Created by Lee Oh Hyoung on 2020/05/31 .. 
 */
package kr.pnu.ga2019.data.network.response

import com.google.gson.annotations.SerializedName
import kr.pnu.ga2019.domain.entity.User

data class UserInsertResponse(

    @SerializedName("data")
    val data: UserPk

): BaseResponse() {

    data class UserPk(

        @SerializedName("memberPk")
        val memberPk: Int = 0
    )
}

fun UserInsertResponse.toEntity(): User =
    User(
        id = data.memberPk,
        locationX = 0.0,
        locationY = 0.0
    )

fun UserInsertResponse.UserPk.toEntity(): User =
    User(
        id = memberPk,
        locationX = 0.0,
        locationY = 0.0
    )