/*
 * Created by Lee Oh Hyoung on 2020/05/31 .. 
 */
package kr.pnu.ga2019.domain.entity

import kr.pnu.ga2019.domain.Entity


data class Path(
    val memberPk: Int,
    val path: List<Point>
): Entity {

    val pathString: String =
        "UserId - $memberPk\n${path.toPathString()}"
}
