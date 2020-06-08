package kr.pnu.ga2019.utility

import kr.pnu.ga2019.R

/*
 * Created by Lee Oh Hyoung on 2020/06/09 .. 
 */
object People {

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
}
