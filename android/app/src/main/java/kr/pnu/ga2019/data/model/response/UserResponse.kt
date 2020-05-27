/*
 * Created by Lee Oh Hyoung on 2020/05/27 .. 
 */
package kr.pnu.ga2019.data.model.response

import com.google.gson.annotations.SerializedName
import kr.pnu.ga2019.domain.entity.User

data class UserResponse(

    @SerializedName("userSeq")
    val userSeq: Int = 0,

    @SerializedName("age")
    val age: Int = 0,

    @SerializedName("createAt")
    val createAt: String = "",

    @SerializedName("updateAt")
    val updateAt: String = ""
)

fun UserResponse.toEntity(): User =
    User(
        id = userSeq,
        locationX = 0.0,
        locationY = 0.0
    )
