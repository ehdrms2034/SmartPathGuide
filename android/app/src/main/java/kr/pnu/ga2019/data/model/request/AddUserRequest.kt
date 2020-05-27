/*
 * Created by Lee Oh Hyoung on 2020/05/27 .. 
 */
package kr.pnu.ga2019.data.model.request

import com.google.gson.annotations.SerializedName
import kr.pnu.ga2019.data.model.Request

data class AddUserRequest(

    @SerializedName("age")
    val age: Int,

    @SerializedName("ancient")
    val ancient: Int,

    @SerializedName("medieval")
    val medieval: Int,

    @SerializedName("modern")
    val modern: Int,

    @SerializedName("donation")
    val donation: Int,

    @SerializedName("painting")
    val painting: Int,

    @SerializedName("world")
    val world: Int,

    @SerializedName("craft")
    val craft: Int

) : Request
