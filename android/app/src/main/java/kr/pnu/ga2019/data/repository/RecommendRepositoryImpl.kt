/*
 * Created by Lee Oh Hyoung on 2020/05/28 .. 
 */
package kr.pnu.ga2019.data.repository

import io.reactivex.Single
import kr.pnu.ga2019.data.RetrofitManager
import kr.pnu.ga2019.data.network.api.RecommendApi
import kr.pnu.ga2019.data.network.response.toEntity
import kr.pnu.ga2019.domain.entity.Point
import kr.pnu.ga2019.domain.repository.RecommendRepository

class RecommendRepositoryImpl : RecommendRepository {

    override fun getRecommend(
        memberPk: Int
    ): Single<List<Point>> =
        RetrofitManager.create(RecommendApi::class.java)
            .getRecommend(
                memberPk = memberPk
            )
            .map { response ->
                response.toEntity()
            }
}
