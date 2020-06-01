/*
 * Created by Lee Oh Hyoung on 2020/05/28 .. 
 */
package kr.pnu.ga2019.data.repository

import io.reactivex.Single
import kr.pnu.ga2019.data.RetrofitManager
import kr.pnu.ga2019.data.network.api.UserApi
import kr.pnu.ga2019.data.network.request.AddUserRequest
import kr.pnu.ga2019.data.network.response.toEntity
import kr.pnu.ga2019.domain.entity.User
import kr.pnu.ga2019.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userApi: UserApi =
        RetrofitManager.create(UserApi::class.java)
) : UserRepository {

    override fun getAll(): Single<List<User>> =
        userApi.getAllUser()
            .map { response -> response.map { it.toEntity() } }

    override fun insert(
        age: Int,
        ancient: Int,
        medieval: Int,
        modern: Int,
        donation: Int,
        painting: Int,
        world: Int,
        craft: Int
    ): Single<User> =
        userApi.addUser(
            AddUserRequest(
                age = age,
                ancient = ancient,
                medieval = medieval,
                modern = modern,
                donation = donation,
                painting = painting,
                world = world,
                craft = craft
            )
        ).map { response -> response.toEntity() }
}
