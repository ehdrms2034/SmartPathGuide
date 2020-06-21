/*
 * Created by Lee Oh Hyoung on 2020/05/28 .. 
 */
package kr.pnu.ga2019.data.network.api

import io.reactivex.Single
import kr.pnu.ga2019.data.network.Api
import kr.pnu.ga2019.data.network.request.PathDataRequest
import kr.pnu.ga2019.data.network.response.RecommendResponse
import kr.pnu.ga2019.data.network.response.UserRecommendPathResponse
import retrofit2.http.*

interface RecommendApi : Api {

    @JvmSuppressWildcards
    @POST("/api/recommend/")
    fun getRecommend(
        @Body pathData: PathDataRequest
    ): Single<UserRecommendPathResponse>
}
