/*
 * Created by Lee Oh Hyoung on 2020/05/28 .. 
 */
package kr.pnu.ga2019.data.repository

import android.util.Log
import io.reactivex.Completable
import kr.pnu.ga2019.data.RetrofitManager
import kr.pnu.ga2019.data.model.PathApi
import kr.pnu.ga2019.domain.repository.PathRepository

class PathRepositoryImpl : PathRepository {

    companion object {
        private const val TAG: String = "PathRepositoryImpl"
    }

    override fun save(
        isAncient: Int,
        isMedieval: Int,
        isModern: Int,
        isDonation: Int,
        isPainting: Int,
        isWorld: Int,
        isCraft: Int
    ): Completable =
        RetrofitManager.create(PathApi::class.java)
            .savePath(isAncient, isMedieval, isModern, isDonation, isPainting, isWorld, isCraft)
            .flatMapCompletable { response ->
                Log.d(TAG, response.message)
                Completable.complete()
            }

}
