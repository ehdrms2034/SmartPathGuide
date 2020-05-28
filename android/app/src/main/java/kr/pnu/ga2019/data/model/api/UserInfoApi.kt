/*
 * Created by Lee Oh Hyoung on 2020/05/28 .. 
 */
package kr.pnu.ga2019.data.model.api

import io.reactivex.Single
import kr.pnu.ga2019.data.model.RetrofitService
import kr.pnu.ga2019.data.model.request.UpdateUserLocationRequest
import kr.pnu.ga2019.data.model.response.CompletableResponse
import kr.pnu.ga2019.data.model.response.GetAllUserLocationResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserInfoApi : RetrofitService {

    @POST("/api/userinfo/userlocation")
    fun postCurrentLocation(
        @Body updateUserLocationRequest: UpdateUserLocationRequest
    ): Single<CompletableResponse>

    @GET("/api/user/userlocation/{memberPk}")
    fun getAllUserLocation(
        @Path("memberPk") memberPk: Int
    ): Single<GetAllUserLocationResponse>

}
