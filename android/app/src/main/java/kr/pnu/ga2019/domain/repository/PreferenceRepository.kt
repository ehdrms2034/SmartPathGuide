/*
 * Created by Lee Oh Hyoung on 2020/06/07 .. 
 */
package kr.pnu.ga2019.domain.repository

import io.reactivex.Completable
import io.reactivex.Single
import kr.pnu.ga2019.domain.Repository
import kr.pnu.ga2019.domain.entity.Preference

interface PreferenceRepository : Repository {

    fun insert(preference: Preference): Completable

    fun delete(preference: Preference): Completable

    fun getPreference(): Single<Preference>

    fun checkUserPreferenceExistence(): Single<Int>

    fun clear(): Completable
}
