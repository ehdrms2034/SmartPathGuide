package kr.pnu.ga2019.utility

import kr.pnu.ga2019.R
import kr.pnu.ga2019.domain.entity.Museum

/*
 * Created by Lee Oh Hyoung on 2020/06/09 .. 
 */
object Const {

    val PERSON_IMAGES: List<Int> = listOf(
        R.drawable.image_person_1,
        R.drawable.image_person_2,
        R.drawable.image_person_3,
        R.drawable.image_person_4,
        R.drawable.image_person_5,
        R.drawable.image_person_6,
        R.drawable.image_person_7
    )
    fun random() = PERSON_IMAGES.random()

    val MUSEUM_POINTS: List<Museum> = listOf(
        Museum(0,"전쟁전시관", R.drawable.img_point_1),
        Museum(1,"고대전시관", R.drawable.img_point_2),
        Museum(2,"중세전시관", R.drawable.img_point_3),
        Museum(3,"현대전시관", R.drawable.img_point_4),
        Museum(4,"미래전시관", R.drawable.img_point_5),
        Museum(5,"예술전시관", R.drawable.img_point_6)
    )
}
