/*
 * Created by Lee Oh Hyoung on 2020/05/26 .. 
 */
package kr.pnu.ga2019.presentation

import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
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

class UserPathViewModel(
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

    fun addUser(
        age: Int,
        ancient: Int,
        medieval: Int,
        modern: Int,
        donation: Int,
        painting: Int,
        world: Int,
        craft: Int
    ) {
        userRepository.insert(
            age = age,
            ancient = ancient,
            medieval = medieval,
            modern = modern,
            donation = donation,
            painting = painting,
            world = world,
            craft = craft
        )
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
            .subscribe(object: CompletableObserver {
                override fun onComplete() {
                    /* explicitly empty */
                }

                override fun onSubscribe(d: Disposable) {
                    /* explicitly empty */
                }

                override fun onError(e: Throwable) {
                    showToast(e.message)
                }
            })

    }

}
