/*
 * Created by Lee Oh Hyoung on 2020/06/08 .. 
 */
package kr.pnu.ga2019.presentation.user

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UserPathViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserPathViewModel::class.java))
            return UserPathViewModel(application = application) as T

        else throw RuntimeException("Can't create ViewModel - Unsupported Viewmodel class")
    }
}
