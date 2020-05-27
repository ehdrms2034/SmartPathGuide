/*
 * Created by Lee Oh Hyoung on 2020/05/27 .. 
 */
package kr.pnu.ga2019.data.model.response

import com.google.gson.annotations.SerializedName
import kr.pnu.ga2019.data.model.Response

data class BaseResponse(

    @SerializedName("data")
    val data: String? = ""

): Response()
