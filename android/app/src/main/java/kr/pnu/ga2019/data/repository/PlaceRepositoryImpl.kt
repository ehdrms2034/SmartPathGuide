/*
 * Created by Lee Oh Hyoung on 2020/06/06 .. 
 */
package kr.pnu.ga2019.data.repository

import io.reactivex.Single
import kr.pnu.ga2019.data.RetrofitManager
import kr.pnu.ga2019.data.network.api.PlaceApi
import kr.pnu.ga2019.data.network.response.toEntity
import kr.pnu.ga2019.domain.entity.Place
import kr.pnu.ga2019.domain.repository.PlaceRepository

class PlaceRepositoryImpl(
    private val placeApi: PlaceApi =
        RetrofitManager.create(PlaceApi::class.java)
) : PlaceRepository {

    override fun getAllPlace(): Single<List<Place>> =
        placeApi.getAllPlace().map { it.toEntity() }

}
