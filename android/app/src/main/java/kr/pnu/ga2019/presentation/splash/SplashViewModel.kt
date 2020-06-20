/*
 * Created by Lee Oh Hyoung on 2020/06/03 .. 
 */
package kr.pnu.ga2019.presentation.splash

import androidx.lifecycle.MutableLiveData
import kr.pnu.ga2019.presentation.base.BaseViewModel

class SplashViewModel : BaseViewModel() {

    val uiState = MutableLiveData<SplashUiState>()
}
