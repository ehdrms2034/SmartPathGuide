/*
 * Created by Lee Oh Hyoung on 2020/05/28 .. 
 */
package kr.pnu.ga2019.data.repository

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Single
import kr.pnu.ga2019.data.RetrofitManager
import kr.pnu.ga2019.data.model.api.UserApi
import kr.pnu.ga2019.data.model.response.toEntity
import kr.pnu.ga2019.domain.entity.User
import kr.pnu.ga2019.domain.repository.UserRepository

class UserRepositoryImpl : UserRepository {

    companion object {
        private const val TAG: String = "UserRepositoryImpl"
    }

    override fun getAll(): Single<List<User>> =
        RetrofitManager.create(UserApi::class.java)
            .getAllUser()
            .map { response ->
                response.map { it.toEntity() }
            }

    override fun insert(
        age: Int,
        ancient: Int,
        medieval: Int,
        modern: Int,
        donation: Int,
        painting: Int,
        world: Int,
        craft: Int
    ): Completable =
        RetrofitManager.create(UserApi::class.java)
            .addUser(
                age,
                ancient,
                medieval,
                modern,
                donation,
                painting,
                world,
                craft
            )
            .flatMapCompletable { response ->
                Log.d(TAG, response.message)
                Completable.complete()
            }
}