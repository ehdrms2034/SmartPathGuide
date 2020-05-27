/*
 * Created by Lee Oh Hyoung on 2020/05/28 .. 
 */
package kr.pnu.ga2019.data.model

import io.reactivex.Single
import kr.pnu.ga2019.data.model.response.BaseResponse

interface PathApi {

    fun savePath(
        isAncient: Int,
        isMedieval: Int,
        isModern: Int,
        isDonation: Int,
        isPainting: Int,
        isWorld: Int,
        isCraft: Int
    ): Single<BaseResponse>

}
