/*
 * Created by Lee Oh Hyoung on 2020/06/20 .. 
 */
package kr.pnu.ga2019.presentation.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.pnu.ga2019.presentation.splash.SplashViewModel

class MainViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SplashViewModel::class.java))
            return MainViewModel(application = application) as T

        else throw RuntimeException("Can't create ViewModel - Unsupported Viewmodel class")
    }
}