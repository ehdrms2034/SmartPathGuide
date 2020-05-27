/*
 * Created by Lee Oh Hyoung on 2020/05/28 .. 
 */
package kr.pnu.ga2019.domain.repository

import io.reactivex.Single
import kr.pnu.ga2019.domain.Repository
import kr.pnu.ga2019.domain.entity.Point

interface RecommendRepository : Repository {

    fun getRecommend(
        memberPk: Int
    ): Single<List<Point>>

}
