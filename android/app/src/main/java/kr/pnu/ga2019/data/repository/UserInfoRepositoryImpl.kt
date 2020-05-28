/*
 * Created by Lee Oh Hyoung on 2020/05/28 .. 
 */
package kr.pnu.ga2019.data.repository

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Single
import kr.pnu.ga2019.data.RetrofitManager
import kr.pnu.ga2019.data.model.api.UserInfoApi
import kr.pnu.ga2019.data.model.request.UpdateUserLocationRequest
import kr.pnu.ga2019.data.model.response.UserInfo
import kr.pnu.ga2019.data.model.response.toEntity
import kr.pnu.ga2019.data.model.response.toList
import kr.pnu.ga2019.domain.entity.User
import kr.pnu.ga2019.domain.repository.UserInfoRepository

class UserInfoRepositoryImpl : UserInfoRepository {

    companion object {
        private const val TAG: String = "UserInfoRepositoryImpl"
    }

    override fun postCurrentLocation(
        memberPk: Int,
        locationX: Double,
        locationY: Double
    ): Completable =
        RetrofitManager.create(UserInfoApi::class.java)
            .postCurrentLocation(
                UpdateUserLocationRequest(
                    memberSeq =  memberPk,
                    locationX = locationX,
                    locationY = locationY
                )
            )
            .flatMapCompletable { response ->
                Log.d(TAG, response.message)
                Completable.complete()
            }

    override fun getAllUserLocation(memberPk: Int): Single<List<User>> =
        RetrofitManager.create(UserInfoApi::class.java)
            .getAllUserLocation(memberPk)
            .map { response -> response.toList() }

}
