/*
 * Created by Lee Oh Hyoung on 2020/06/01 .. 
 */
package kr.pnu.ga2019.presentation.personal

import kr.pnu.ga2019.data.repository.PathRepositoryImpl
import kr.pnu.ga2019.data.repository.RecommendRepositoryImpl
import kr.pnu.ga2019.data.repository.UserInfoRepositoryImpl
import kr.pnu.ga2019.data.repository.UserRepositoryImpl
import kr.pnu.ga2019.domain.repository.PathRepository
import kr.pnu.ga2019.domain.repository.RecommendRepository
import kr.pnu.ga2019.domain.repository.UserInfoRepository
import kr.pnu.ga2019.domain.repository.UserRepository
import kr.pnu.ga2019.presentation.base.BaseViewModel
import kr.pnu.ga2019.util.AppSchedulerProvider
import kr.pnu.ga2019.util.BaseSchedulerProvider

class PersonalPathViewModel(
    private val userRepository: UserRepository =
        UserRepositoryImpl(),
    private val userInfoRepository: UserInfoRepository =
        UserInfoRepositoryImpl(),
    private val pathRepository: PathRepository =
        PathRepositoryImpl(),
    private val recommendRepository: RecommendRepository =
        RecommendRepositoryImpl(),
    private val scheduler: BaseSchedulerProvider =
        AppSchedulerProvider()
) : BaseViewModel() {
    /* explicitly empty */
}
