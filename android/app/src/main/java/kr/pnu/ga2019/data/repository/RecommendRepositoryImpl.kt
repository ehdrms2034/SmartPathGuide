/*
 * Created by Lee Oh Hyoung on 2020/05/28 .. 
 */
package kr.pnu.ga2019.data.repository

import io.reactivex.Single
import kr.pnu.ga2019.data.RetrofitManager
import kr.pnu.ga2019.data.network.api.RecommendApi
import kr.pnu.ga2019.data.network.request.PathDataRequest
import kr.pnu.ga2019.data.network.response.toEntity
import kr.pnu.ga2019.domain.entity.PathData
import kr.pnu.ga2019.domain.entity.Point
import kr.pnu.ga2019.domain.entity.RecommendedPlace
import kr.pnu.ga2019.domain.repository.RecommendRepository

class RecommendRepositoryImpl(
    private val recommendApi: RecommendApi =
        RetrofitManager.create(RecommendApi::class.java)
) : RecommendRepository {

    override fun getRecommend(pathData: List<PathData>): Single<RecommendedPlace> =
        recommendApi.getRecommend(PathDataRequest(pathData = pathData))
            .map { it.toEntity() }
}
