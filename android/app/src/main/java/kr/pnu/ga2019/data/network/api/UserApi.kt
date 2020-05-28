/*
 * Created by Lee Oh Hyoung on 2020/05/28 .. 
 */
package kr.pnu.ga2019.data.network.api

import io.reactivex.Single
import kr.pnu.ga2019.data.network.Api
import kr.pnu.ga2019.data.network.request.AddUserRequest
import kr.pnu.ga2019.data.network.response.CompletableResponse
import kr.pnu.ga2019.data.network.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi : Api {

    @GET("/api/member/members")
    fun getAllUser(): Single<List<UserResponse>>

    @POST("/api/member/")
    fun addUser(
        @Body addUserRequest: AddUserRequest
    ): Single<CompletableResponse>

}
