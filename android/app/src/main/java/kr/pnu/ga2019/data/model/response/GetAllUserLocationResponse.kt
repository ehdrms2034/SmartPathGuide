/*
 * Created by Lee Oh Hyoung on 2020/05/27 .. 
 */
package kr.pnu.ga2019.data.model.response

import com.google.gson.annotations.SerializedName
import kr.pnu.ga2019.data.model.Response
import kr.pnu.ga2019.data.model.UserInfo

data class GetAllUserLocationResponse(

    @SerializedName("data")
    val data: List<UserLocation>

): Response() {

    data class UserLocation(
        @SerializedName("myLocation")
        val myLocation: UserInfo,

        @SerializedName("userData")
        val userData: List<UserInfo>
    )

}
