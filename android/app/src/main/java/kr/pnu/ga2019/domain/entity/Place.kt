/*
 * Created by Lee Oh Hyoung on 2020/06/06 .. 
 */
package kr.pnu.ga2019.domain.entity

import kr.pnu.ga2019.domain.Entity

data class Place(
    val id: Int,
    val name: String,
    val maxPeople: Int,
    val locationX: Int,
    val locationY: Int
) : Entity
