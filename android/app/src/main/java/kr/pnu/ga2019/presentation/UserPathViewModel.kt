/*
 * Created by Lee Oh Hyoung on 2020/05/26 .. 
 */
package kr.pnu.ga2019.presentation

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

class UserPathViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}
