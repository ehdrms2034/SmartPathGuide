/*
 * Created by Lee Oh Hyoung on 2020/06/03 .. 
 */
package kr.pnu.ga2019.presentation.splash

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import kr.pnu.ga2019.data.repository.PreferenceRepositoryImpl
import kr.pnu.ga2019.domain.entity.Preference
import kr.pnu.ga2019.domain.repository.PreferenceRepository
import kr.pnu.ga2019.presentation.base.BaseViewModel
import kr.pnu.ga2019.util.AppSchedulerProvider
import kr.pnu.ga2019.util.BaseSchedulerProvider

class SplashViewModel(
    application: Application,
    private val preferenceRepository: PreferenceRepository =
        PreferenceRepositoryImpl(application.applicationContext),
    private val scheduler: BaseSchedulerProvider =
        AppSchedulerProvider()
) : BaseViewModel(application) {

    val preferenceState = MutableLiveData<SplashUiState>()

    fun clearUserPreference() =
        preferenceRepository.clear()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
            .subscribe({
                Logger.d("Clear User Preference")
            }, { throwable ->
                logError(throwable)
            })
            .addDisposable()

    fun checkUserPreferenceExistence() =
        preferenceRepository.checkUserPreferenceExistence()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
            .subscribe({ count ->
                if(count != 1)
                    preferenceState.value = setEmptyState()
                else
                    getUserPreference()
            }, { throwable ->
                logError(throwable)
            })
            .addDisposable()

    fun setUserPreference(preference: Preference) =
        preferenceRepository.insert(preference = preference)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
            .subscribe()
            .addDisposable()

    private fun getUserPreference() =
        preferenceRepository.getPreference()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
            .subscribe({ preference ->
                preferenceState.value = setAvailableState(preference)
            }, { throwable ->
                logError(throwable)
            })
            .addDisposable()
}
