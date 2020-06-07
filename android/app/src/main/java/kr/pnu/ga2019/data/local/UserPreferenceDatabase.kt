/*
 * Created by Lee Oh Hyoung on 2020/06/07 .. 
 */
package kr.pnu.ga2019.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1, entities = [UserPreference::class], exportSchema = false)
abstract class UserPreferenceDatabase : RoomDatabase() {

    abstract fun userPreferenceDao(): UserPreferenceDao

    companion object {

        private var instance: UserPreferenceDatabase? = null

        @Synchronized
        fun getInstance(context: Context): UserPreferenceDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                    UserPreferenceDatabase::class.java, "preference.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return instance!!
        }
    }
}
