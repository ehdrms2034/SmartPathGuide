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
import kr.pnu.ga2019.utility.AppSchedulerProvider
import kr.pnu.ga2019.utility.BaseSchedulerProvider

class SplashViewModel(
    application: Application,
    private val preferenceRepository: PreferenceRepository =
        PreferenceRepositoryImpl(application.applicationContext),
    private val scheduler: BaseSchedulerProvider =
        AppSchedulerProvider()
) : BaseViewModel(application) {

    val uiState = MutableLiveData<SplashUiState>()

    fun clearUserPreference() =
        preferenceRepository.clear()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
            .subscribe({
                Logger.d("Clear User Preference")
            }, { throwable -> uiState.value = asErrorState(throwable) })
            .addDisposable()

    fun checkUserPreferenceExistence() =
        preferenceRepository.checkUserPreferenceExistence()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
            .subscribe({ count ->
                if(count != 1)
                    uiState.value = setEmptyState()
                else
                    getUserPreference()
            }, { throwable -> uiState.value = asErrorState(throwable) })
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
                Logger.d("저장된 유저 성향 : $preference")
                uiState.value = setAvailableState(preference)
            }, { throwable -> uiState.value = asErrorState(throwable) })
            .addDisposable()
}
