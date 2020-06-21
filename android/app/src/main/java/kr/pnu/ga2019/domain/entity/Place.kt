/*
 * Created by Lee Oh Hyoung on 2020/06/06 .. 
 */
package kr.pnu.ga2019.domain.entity

import kr.pnu.ga2019.domain.Entity

data class Place(
    val name: String,
    val maxPeople: Int,
    var locationX: Int,
    var locationY: Int
) : Entity
