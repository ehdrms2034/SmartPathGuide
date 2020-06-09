/*
 * Created by Lee Oh Hyoung on 2020/06/07 .. 
 */
package kr.pnu.ga2019.presentation.splash

import kr.pnu.ga2019.domain.entity.Preference
import kr.pnu.ga2019.presentation.UiState

sealed class SplashUiState : UiState {
    abstract val isExist: Boolean

    data class Available(
        override val isExist: Boolean,
        val preference: Preference
    ) : SplashUiState()

    data class Empty(
        override val isExist: Boolean,
        val message: String
    ) : SplashUiState()

    data class Error(
        override val isExist: Boolean,
        val throwable: Throwable
    ) : SplashUiState()

}
