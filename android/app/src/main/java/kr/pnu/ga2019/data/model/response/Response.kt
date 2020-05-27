/*
 * Created by Lee Oh Hyoung on 2020/05/27 .. 
 */
package kr.pnu.ga2019.data.model.response

import com.google.gson.annotations.SerializedName

abstract class Response(

    @SerializedName("response")
    val response: String = "",

    @SerializedName("message")
    val message: String = ""
)
