/*
 * Created by Lee Oh Hyoung on 2020/05/27 .. 
 */
package kr.pnu.ga2019.data.model.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @SerializedName("userSeq")
    val userSeq: Int = 0,

    @SerializedName("age")
    val age: Int = 0,

    @SerializedName("createAt")
    val createAt: String = "",

    @SerializedName("updateAt")
    val updateAt: String = ""
)
