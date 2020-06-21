/*
 * Created by Lee Oh Hyoung on 2020/05/28 .. 
 */
package kr.pnu.ga2019.domain.repository

import io.reactivex.Single
import kr.pnu.ga2019.domain.Repository
import kr.pnu.ga2019.domain.entity.PathData
import kr.pnu.ga2019.domain.entity.RecommendedPlace

interface RecommendRepository : Repository {

    fun getRecommend(
        pathData: List<PathData>
    ): Single<RecommendedPlace>

}
