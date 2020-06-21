/*
 * Created by Lee Oh Hyoung on 2020/06/21 .. 
 */
package kr.pnu.ga2019.domain.entity

import kr.pnu.ga2019.domain.Entity

data class RecommendedPlace(
    val first: PlaceInfo,
    val second: PlaceInfo,
    val third: PlaceInfo
): Entity

data class PlaceInfo(
    val placeId: Int,
    val name: String,
    val percent: Double
): Entity