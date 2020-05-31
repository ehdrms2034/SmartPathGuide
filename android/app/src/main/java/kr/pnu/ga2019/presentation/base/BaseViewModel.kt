/*
 * Created by Lee Oh Hyoung on 2020/05/29 .. 
 */
package kr.pnu.ga2019.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kr.pnu.ga2019.util.SingleLiveEvent

abstract class BaseViewModel : ViewModel() {

    companion object {
        private const val TAG: String = "BaseViewModel"
    }

    private val compositeDisposable = CompositeDisposable()

    private val _showErrorMessage = SingleLiveEvent<String?>()
    val showErrorMessage: LiveData<String?> get() = _showErrorMessage

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun clear() {
        if(!compositeDisposable.isDisposed)
            compositeDisposable.clear()
        else {
            Logger.e("compositeDisposable is already disposed")
            showToast("compositeDisposable is already disposed")
        }
    }

    fun Disposable.addDisposable() =
        compositeDisposable.add(this)

    fun showToast(message: String?) =
        _showErrorMessage.postValue(message)

    fun logError(throwable: Throwable) =
        Logger.log(Logger.ERROR, TAG, throwable.message.toString(), throwable)
}
