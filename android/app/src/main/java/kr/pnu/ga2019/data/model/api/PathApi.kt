/*
 * Created by Lee Oh Hyoung on 2020/05/28 .. 
 */
package kr.pnu.ga2019.data.model.api

import io.reactivex.Single
import kr.pnu.ga2019.data.model.RetrofitService
import kr.pnu.ga2019.data.model.request.SavePathRequest
import kr.pnu.ga2019.data.model.response.CompletableResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface PathApi : RetrofitService {

    @POST("/api/path")
    fun savePath(
        @Body savePathRequest: SavePathRequest
    ): Single<CompletableResponse>

}
