/*
 * Created by Lee Oh Hyoung on 2020/06/07 .. 
 */
package kr.pnu.ga2019.presentation.splash

import kr.pnu.ga2019.domain.entity.Preference
import kr.pnu.ga2019.presentation.base.BaseViewModel

fun SplashUiState.asEmptyState(): SplashUiState.Empty =
    SplashUiState.Empty(
        isExist = false,
        message = "user preference not exist"
    )

fun SplashUiState.asAvailableState(preference: Preference): SplashUiState.Available =
    SplashUiState.Available(
        isExist = true,
        preference = preference
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
