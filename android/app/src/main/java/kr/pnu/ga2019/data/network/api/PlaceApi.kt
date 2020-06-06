/*
 * Created by Lee Oh Hyoung on 2020/06/06 .. 
 */
package kr.pnu.ga2019.data.network.api

import io.reactivex.Single
import kr.pnu.ga2019.data.network.Api
import kr.pnu.ga2019.data.network.response.GetAllPlaceResponse
import retrofit2.http.GET

interface PlaceApi : Api {

    @GET("/api/place/places")
    fun getAllPlace(): Single<GetAllPlaceResponse>
}
