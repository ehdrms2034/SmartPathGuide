/*
 * Created by Lee Oh Hyoung on 2020/05/27 .. 
 */
package kr.pnu.ga2019.data.model.request

import com.google.gson.annotations.SerializedName

data class SavePathRequest(

    @SerializedName("isAncient")
    val isAncient: Int,

    @SerializedName("isMedieval")
    val isMedieval: Int,

    @SerializedName("isModern")
    val isModern: Int,

    @SerializedName("isDonation")
    val isDonation: Int,

    @SerializedName("isPainting")
    val isPainting: Int,

    @SerializedName("isWorld")
    val isWorld: Int,

    @SerializedName("isCraft")
    val isCraft: Int
)
