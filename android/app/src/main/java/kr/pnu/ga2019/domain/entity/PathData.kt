/*
 * Created by Lee Oh Hyoung on 2020/06/21 .. 
 */
package kr.pnu.ga2019.domain.entity

import androidx.annotation.IntRange

data class PathData(
    val seq: Int = 0,
    val placeId: Int = 0,

    @IntRange(from = 0, to = 30)
    val stayTime: Int = 0
)