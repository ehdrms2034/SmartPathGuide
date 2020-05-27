/*
 * Created by Lee Oh Hyoung on 2020/05/28 .. 
 */
package kr.pnu.ga2019.domain.repository

import io.reactivex.Completable
import kr.pnu.ga2019.domain.Repository

interface PathRepository : Repository {

    fun save(
        isAncient: Int,
        isMedieval: Int,
        isModern: Int,
        isDonation: Int,
        isPainting: Int,
        isWorld: Int,
        isCraft: Int
    ): Completable

}
