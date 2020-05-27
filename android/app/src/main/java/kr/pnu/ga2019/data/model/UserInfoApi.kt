/*
 * Created by Lee Oh Hyoung on 2020/05/28 .. 
 */
package kr.pnu.ga2019.data.model

import io.reactivex.Single
import kr.pnu.ga2019.data.model.response.BaseResponse
import kr.pnu.ga2019.data.model.response.GetAllUserLocationResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserInfoApi {

    @POST("/api/userinfo/userlocation")
    fun postCurrentLocation(
        memberPk: Int,
        locationX: Double,
        locationY: Double
    ): Single<BaseResponse>

    @GET("/api/user/userlocation/{memberPk}")
    fun getAllUserLocation(
        @Path("memberPk") memberPk: Int
    ): Single<GetAllUserLocationResponse>

}
