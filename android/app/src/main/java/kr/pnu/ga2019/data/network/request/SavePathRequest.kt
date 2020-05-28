/*
 * Created by Lee Oh Hyoung on 2020/05/27 .. 
 */
package kr.pnu.ga2019.data.network.request

import com.google.gson.annotations.SerializedName
import kr.pnu.ga2019.data.network.Request

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
) : Request
