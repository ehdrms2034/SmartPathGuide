/*
 * Created by Lee Oh Hyoung on 2020/06/07 .. 
 */
package kr.pnu.ga2019.domain.entity

import kr.pnu.ga2019.domain.Entity
import java.io.Serializable

data class Preference(
    val age: Int,
    val ancient: Int,
    val medieval: Int,
    val modern: Int,
    val donation: Int,
    val painting: Int,
    val world: Int,
    val craft: Int
): Entity, Serializable
