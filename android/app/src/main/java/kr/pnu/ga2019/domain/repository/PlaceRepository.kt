/*
 * Created by Lee Oh Hyoung on 2020/06/06 .. 
 */
package kr.pnu.ga2019.domain.repository

import io.reactivex.Single
import kr.pnu.ga2019.domain.Repository
import kr.pnu.ga2019.domain.entity.Place

interface PlaceRepository : Repository {

    fun getAllPlace(): Single<List<Place>>
}
