/*
 * Created by Lee Oh Hyoung on 2020/06/07 .. 
 */
package kr.pnu.ga2019.data.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserPreferenceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userPreference: UserPreference): Completable

    @Delete
    fun delete(userPreference: UserPreference): Completable

    @Query("SELECT * FROM preference LIMIT 1")
    fun getPreference(): Single<UserPreference>

    @Query("SELECT count(*) FROM preference")
    fun checkUserPreferenceExistence(): Single<Int>

    @Query("DELETE FROM preference")
    fun clear(): Completable
}
