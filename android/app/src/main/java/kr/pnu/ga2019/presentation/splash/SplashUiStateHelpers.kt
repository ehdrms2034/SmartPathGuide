/*
 * Created by Lee Oh Hyoung on 2020/06/07 .. 
 */
package kr.pnu.ga2019.presentation.splash

import kr.pnu.ga2019.domain.entity.Preference
import kr.pnu.ga2019.presentation.base.BaseViewModel

fun Throwable.asEmptyState(): SplashUiState.Empty =
    SplashUiState.Empty(
        isExist = false,
        message = this.message.toString()
    )

fun Throwable.asFailureState(): SplashUiState.Failure =
    SplashUiState.Failure(
        isExist = false,
        throwable = this
    )

fun String?.asEmptyState(): SplashUiState.Empty =
    SplashUiState.Empty(
        isExist = false,
        message = if(this.isNullOrEmpty()) "user preference not exist" else this
    )

fun Preference.asAvailableState(): SplashUiState.Available =
    SplashUiState.Available(
        isExist = true,
        preference = this
    )

fun BaseViewModel.setEmptyState(): SplashUiState.Empty =
    SplashUiState.Empty(
        isExist = false,
        message = "user preference not exist"
    )

fun BaseViewModel.setAvailableState(preference: Preference): SplashUiState.Available =
    SplashUiState.Available(
        isExist = true,
        preference = preference
    )

fun BaseViewModel.setFailureState(throwable: Throwable): SplashUiState.Failure =
    SplashUiState.Failure(
        isExist = false,
        throwable = throwable
    )
