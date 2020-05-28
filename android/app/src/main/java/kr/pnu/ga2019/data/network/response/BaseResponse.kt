/*
 * Created by Lee Oh Hyoung on 2020/05/27 .. 
 */
package kr.pnu.ga2019.data.network.response

import com.google.gson.annotations.SerializedName
import kr.pnu.ga2019.data.network.Response

abstract class BaseResponse(

    @SerializedName("response")
    val response: String = "",

    @SerializedName("message")
    val message: String = ""
): Response
