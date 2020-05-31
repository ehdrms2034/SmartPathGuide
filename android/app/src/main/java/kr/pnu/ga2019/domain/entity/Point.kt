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

    val myLocation: String =
        "$name - (${locationX.toInt()}, ${locationY.toInt()})\n"

    val point: String =
        "$seq. $name - (${locationX.toInt()}, ${locationY.toInt()})\n"
}

fun List<Point>.toPathString(): String =
    StringBuilder().apply {
        this@toPathString
            .also { points ->
                points
                    .filter { point -> point.name == "myLocation" }
                    .forEach { append(it.myLocation) }
            }
            .filter { it.name != "myLocation" }
            .forEach { append(it.point) }
    }.toString()
