/*
 * Created by Lee Oh Hyoung on 2020/05/27 .. 
 */
package kr.pnu.ga2019.data.model.response

import com.google.gson.annotations.SerializedName

data class GetAllUserLocationResponse(

    @SerializedName("data")
    val data: UserLocation

): BaseResponse() {

    data class UserLocation(
        @SerializedName("myLocation")
        val myLocation: UserInfo,

        @SerializedName("userData")
        val userData: List<UserInfo>
    )

}
