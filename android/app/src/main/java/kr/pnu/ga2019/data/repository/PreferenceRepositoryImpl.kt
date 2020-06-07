/*
 * Created by Lee Oh Hyoung on 2020/06/07 .. 
 */
package kr.pnu.ga2019.data.repository

import android.content.Context
import io.reactivex.Completable
import io.reactivex.Single
import kr.pnu.ga2019.data.local.UserPreferenceDatabase
import kr.pnu.ga2019.data.local.toEntity
import kr.pnu.ga2019.data.local.toRoomObject
import kr.pnu.ga2019.domain.entity.Preference
import kr.pnu.ga2019.domain.repository.PreferenceRepository

class PreferenceRepositoryImpl(
    context: Context,
    private val userPreferenceDatabase: UserPreferenceDatabase =
        UserPreferenceDatabase.getInstance(context)
) : PreferenceRepository {

    override fun insert(preference: Preference): Completable =
        userPreferenceDatabase
            .userPreferenceDao()
            .insert(preference.toRoomObject())

    override fun delete(preference: Preference): Completable =
        userPreferenceDatabase
            .userPreferenceDao()
            .delete(preference.toRoomObject())

    override fun getPreference(): Single<Preference> =
        userPreferenceDatabase.userPreferenceDao()
            .getPreference()
            .map { it.toEntity() }

    override fun checkUserPreferenceExistence(): Single<Int> =
        userPreferenceDatabase
            .userPreferenceDao()
            .checkUserPreferenceExistence()

    override fun clear(): Completable =
        userPreferenceDatabase.userPreferenceDao()
            .clear()
}
