/*
 * Created by Lee Oh Hyoung on 2020/06/10 .. 
 */
package kr.pnu.ga2019.presentation.user

import kr.pnu.ga2019.domain.entity.Path
import kr.pnu.ga2019.domain.entity.Place

private fun Throwable.asErrorState(): UserPathUiState.Error =
    UserPathUiState.Error(
        state = "error is occur",
        throwable = this
    )

private fun Path.asVisitorPathState(): UserPathUiState.LoadVisitorPath =
    UserPathUiState.LoadVisitorPath(
        state = "visitor",
        path = this
    )

private fun Path.asMyPathState(): UserPathUiState.LoadMyPath =
    UserPathUiState.LoadMyPath(
        state = "myPath",
        path = this
    )

private fun List<Place>.asPlaceState(): UserPathUiState.LoadPlace =
    UserPathUiState.LoadPlace(
        state = "places",
        places = this
    )

fun UserPathViewModel.setVisitorSetState() {
    uiState.value = UserPathUiState.SetVisitor
}

fun UserPathViewModel.setErrorState(throwable: Throwable) {
    uiState.value = throwable.asErrorState()
}

fun UserPathViewModel.setVisitorPathState(path: Path) {
    uiState.value = path.asVisitorPathState()
}

fun UserPathViewModel.setPlaceState(list: List<Place>) {
    uiState.value = list.asPlaceState()
}

fun UserPathViewModel.setMyPathState(path: Path) {
    uiState.value = path.asMyPathState()
}
