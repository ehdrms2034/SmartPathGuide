/*
 * Created by Lee Oh Hyoung on 2020/05/28 .. 
 */
package kr.pnu.ga2019.domain.entity

import kr.pnu.ga2019.domain.Entity

data class User(
    val id: Int,
    val locationX: Double,
    val locationY: Double
) : Entity {

    val location: String =
        "Location: ($locationX, $locationY)"
}
