/*
 * Created by Lee Oh Hyoung on 2020/05/28 .. 
 */
package kr.pnu.ga2019.data.repository

import io.reactivex.Single
import kr.pnu.ga2019.data.RetrofitManager
import kr.pnu.ga2019.data.network.api.PathApi
import kr.pnu.ga2019.data.network.api.RecommendApi
import kr.pnu.ga2019.data.network.response.toEntity
import kr.pnu.ga2019.domain.entity.Point
import kr.pnu.ga2019.domain.repository.RecommendRepository

class RecommendRepositoryImpl(
    private val recommendApi: RecommendApi =
        RetrofitManager.create(RecommendApi::class.java)
) : RecommendRepository {

    override fun getRecommend(
        memberPk: Int
    ): Single<List<Point>> =
        recommendApi.getRecommend(memberPk = memberPk)
            .map { response -> response.toEntity() }
}
