/*
 * Created by Lee Oh Hyoung on 2020/05/28 .. 
 */
package kr.pnu.ga2019.data.model.api

import io.reactivex.Single
import kr.pnu.ga2019.data.model.RetrofitService
import kr.pnu.ga2019.data.model.response.RecommendResponse

interface RecommendApi : RetrofitService {

    fun getRecommend(memberPk: Int): Single<RecommendResponse>
}
