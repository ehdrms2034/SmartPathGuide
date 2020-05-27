/*
 * Created by Lee Oh Hyoung on 2020/05/28 .. 
 */
package kr.pnu.ga2019.data.model.api

import io.reactivex.Single
import kr.pnu.ga2019.data.model.RetrofitService
import kr.pnu.ga2019.data.model.response.CompletableResponse
import kr.pnu.ga2019.data.model.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi : RetrofitService {

    @GET("/api/member/members")
    fun getAllUser(): Single<List<UserResponse>>

    @POST("/api/member/")
    fun addUser(
        age: Int,
        ancient: Int,
        medieval: Int,
        modern: Int,
        donation: Int,
        painting: Int,
        world: Int,
        craft: Int
    ): Single<CompletableResponse>

}
