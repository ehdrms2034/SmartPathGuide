/*
 * Created by Lee Oh Hyoung on 2020/05/28 .. 
 */
package kr.pnu.ga2019.domain.repository

import io.reactivex.Completable
import io.reactivex.Single
import kr.pnu.ga2019.domain.Repository
import kr.pnu.ga2019.domain.entity.User

interface UserInfoRepository : Repository {

    fun updateCurrentLocation(
        memberPk: Int,
        locationX: Int,
        locationY: Int
    ): Completable

    fun getAllUserLocation(memberPk: Int): Single<List<User>>

}
