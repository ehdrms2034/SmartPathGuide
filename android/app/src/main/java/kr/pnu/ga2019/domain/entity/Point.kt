/*
 * Created by Lee Oh Hyoung on 2020/05/28 .. 
 */
package kr.pnu.ga2019.domain.entity

import kr.pnu.ga2019.domain.Entity

data class Point(
    val seq: Int,
    val name: String,
    val locationX: Double,
    val locationY: Double
) : Entity {

    val point: String =
        "$name - ($locationX, $locationY)\n"
}

fun List<Point>.toPathString(): String =
    StringBuilder().apply {
        this@toPathString.forEach {
            append(it.point)
        }
    }.toString()