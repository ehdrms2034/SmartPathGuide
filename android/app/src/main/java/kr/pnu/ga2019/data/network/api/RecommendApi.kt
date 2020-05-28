/*
 * Created by Lee Oh Hyoung on 2020/05/28 .. 
 */
package kr.pnu.ga2019.data.network.api

import io.reactivex.Single
import kr.pnu.ga2019.data.network.Api
import kr.pnu.ga2019.data.network.response.RecommendResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RecommendApi : Api {

    @GET("/api/recommend/{memberPk}")
    fun getRecommend(
        @Path("memberPk") memberPk: Int
    ): Single<RecommendResponse>
}
