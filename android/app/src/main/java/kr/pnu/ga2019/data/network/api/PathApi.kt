/*
 * Created by Lee Oh Hyoung on 2020/05/28 .. 
 */
package kr.pnu.ga2019.data.network.api

import io.reactivex.Completable
import kr.pnu.ga2019.data.network.Api
import kr.pnu.ga2019.data.network.request.SavePathRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface PathApi : Api {

    @POST("/api/path")
    fun savePath(
        @Body savePathRequest: SavePathRequest
    ): Completable

}
