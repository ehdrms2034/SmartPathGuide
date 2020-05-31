/*
 * Created by Lee Oh Hyoung on 2020/05/28 .. 
 */
package kr.pnu.ga2019.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import kr.pnu.ga2019.data.RetrofitManager
import kr.pnu.ga2019.data.network.api.UserInfoApi
import kr.pnu.ga2019.data.network.request.UpdateUserLocationRequest
import kr.pnu.ga2019.data.network.response.toList
import kr.pnu.ga2019.domain.entity.User
import kr.pnu.ga2019.domain.repository.UserInfoRepository

class UserInfoRepositoryImpl : UserInfoRepository {

    companion object {
        private const val TAG: String = "UserInfoRepositoryImpl"
    }

    override fun updateCurrentLocation(
        memberPk: Int,
        locationX: Int,
        locationY: Int
    ): Completable =
        RetrofitManager.create(UserInfoApi::class.java)
            .updateCurrentLocation(
                UpdateUserLocationRequest(
                    memberSeq =  memberPk,
                    locationX = locationX,
                    locationY = locationY
                )
            )

    override fun getAllUserLocation(memberPk: Int): Single<List<User>> =
        RetrofitManager.create(UserInfoApi::class.java)
            .getAllUserLocation(memberPk)
            .map { response -> response.toList() }

}
