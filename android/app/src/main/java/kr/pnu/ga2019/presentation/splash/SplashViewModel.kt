/*
 * Created by Lee Oh Hyoung on 2020/06/03 .. 
 */
package kr.pnu.ga2019.presentation.splash

import androidx.lifecycle.LiveData
import kr.pnu.ga2019.presentation.base.BaseViewModel
import kr.pnu.ga2019.util.SingleLiveEvent

class SplashViewModel : BaseViewModel() {

    private val _start = SingleLiveEvent<Any>()
    val start: LiveData<Any> get() = _start

    fun startActivity() = _start.call()

}
