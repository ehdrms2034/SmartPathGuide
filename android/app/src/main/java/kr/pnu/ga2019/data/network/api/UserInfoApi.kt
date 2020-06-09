/*
 * Created by Lee Oh Hyoung on 2020/05/28 .. 
 */
package kr.pnu.ga2019.data.network.api

import io.reactivex.Completable
import io.reactivex.Single
import kr.pnu.ga2019.data.network.Api
import kr.pnu.ga2019.data.network.request.UpdateUserLocationRequest
import kr.pnu.ga2019.data.network.response.GetAllUserLocationResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserInfoApi : Api {

    @POST("/api/userinfo/userlocation")
    fun updateCurrentLocation(
        @Body updateUserLocationRequest: UpdateUserLocationRequest
    ): Completable

    @GET("/api/user/userlocation/{memberPk}")
    fun getAllUserLocation(
        @Path("memberPk") memberPk: Int
    ): Single<GetAllUserLocationResponse>

}
