/*
 * Created by Lee Oh Hyoung on 2020/06/20 .. 
 */
package kr.pnu.ga2019.utility

import android.content.res.Resources

fun Int.dpToPixel(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()