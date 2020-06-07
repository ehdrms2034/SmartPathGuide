/*
 * Created by Lee Oh Hyoung on 2020/06/07 .. 
 */
package kr.pnu.ga2019.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kr.pnu.ga2019.domain.entity.Preference

@Entity(tableName = "preference")
data class UserPreference(
    @PrimaryKey
    val userId: Int = 10000,
    val age: Int,
    val ancient: Int,
    val medieval: Int,
    val modern: Int,
    val donation: Int,
    val painting: Int,
    val world: Int,
    val craft: Int
): RoomObject

fun UserPreference.toEntity(): Preference =
    Preference(
        age = age,
        ancient = ancient,
        medieval = medieval,
        modern = modern,
        donation = donation,
        painting = painting,
        world = world,
        craft = craft
    )

fun Preference.toRoomObject(): UserPreference =
    UserPreference(
        age = age,
        ancient = ancient,
        medieval = medieval,
        modern = modern,
        donation = donation,
        painting = painting,
        world = world,
        craft = craft
    )
