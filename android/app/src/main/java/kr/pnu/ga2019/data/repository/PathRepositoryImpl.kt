/*
 * Created by Lee Oh Hyoung on 2020/05/28 .. 
 */
package kr.pnu.ga2019.data.repository

import io.reactivex.Completable
import kr.pnu.ga2019.data.RetrofitManager
import kr.pnu.ga2019.data.network.api.PathApi
import kr.pnu.ga2019.data.network.request.SavePathRequest
import kr.pnu.ga2019.domain.repository.PathRepository

class PathRepositoryImpl(
    private val pathApi: PathApi =
        RetrofitManager.create(PathApi::class.java)
) : PathRepository {

    override fun save(
        isAncient: Int,
        isMedieval: Int,
        isModern: Int,
        isDonation: Int,
        isPainting: Int,
        isWorld: Int,
        isCraft: Int
    ): Completable =
        pathApi.savePath(
            SavePathRequest(
                isAncient= isAncient,
                isMedieval = isMedieval,
                isModern = isModern,
                isDonation = isDonation,
                isPainting = isPainting,
                isWorld = isWorld,
                isCraft = isCraft
            )
        )
}
