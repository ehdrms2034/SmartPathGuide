/*
 * Created by Lee Oh Hyoung on 2020/06/09 .. 
 */
package kr.pnu.ga2019.presentation.user

import kr.pnu.ga2019.domain.entity.Path
import kr.pnu.ga2019.domain.entity.Place

sealed class UserPathUiState {

    abstract val state: String

    object SetVisitor : UserPathUiState() {
        override val state: String
            get() = "visitor setting is done"
    }

    data class LoadVisitorPath(
        override val state: String,
        val path: Path
    ): UserPathUiState()

    data class LoadMyPath(
        override val state: String,
        val path: Path
    ): UserPathUiState()

    data class LoadPlace(
        override val state: String,
        val places: List<Place>
    ): UserPathUiState()

    data class Error(
        override val state: String,
        val throwable: Throwable
    ): UserPathUiState()

}
