/*
 * Created by Lee Oh Hyoung on 2020/05/27 .. 
 */
package kr.pnu.ga2019.data.network.response

import com.google.gson.annotations.SerializedName
import kr.pnu.ga2019.domain.entity.User

data class GetAllUserLocationResponse(

    @SerializedName("data")
    val data: UserLocation?

): BaseResponse() {

    data class UserLocation(
        @SerializedName("myLocation")
        val myLocation: UserInfo,

        @SerializedName("userData")
        val userData: List<UserInfo>
    )

}

fun GetAllUserLocationResponse.toList(): List<User> =
    ArrayList<UserInfo>().apply {
        data?.run {
            add(myLocation)
            addAll(userData)
        }
    }.map { userInfo ->
        User(
            id = userInfo.memberSeq,
            locationX = userInfo.locationX,
            locationY = userInfo.locationY
        )
    }
