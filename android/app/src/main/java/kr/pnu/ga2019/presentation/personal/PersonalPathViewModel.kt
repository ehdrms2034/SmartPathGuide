/*
 * Created by Lee Oh Hyoung on 2020/06/01 .. 
 */
package kr.pnu.ga2019.presentation.personal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.CompletableObserver
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import kr.pnu.ga2019.data.repository.PathRepositoryImpl
import kr.pnu.ga2019.data.repository.RecommendRepositoryImpl
import kr.pnu.ga2019.data.repository.UserInfoRepositoryImpl
import kr.pnu.ga2019.data.repository.UserRepositoryImpl
import kr.pnu.ga2019.domain.entity.Path
import kr.pnu.ga2019.domain.entity.Point
import kr.pnu.ga2019.domain.entity.User
import kr.pnu.ga2019.domain.repository.PathRepository
import kr.pnu.ga2019.domain.repository.RecommendRepository
import kr.pnu.ga2019.domain.repository.UserInfoRepository
import kr.pnu.ga2019.domain.repository.UserRepository
import kr.pnu.ga2019.presentation.base.BaseViewModel
import kr.pnu.ga2019.util.AppSchedulerProvider
import kr.pnu.ga2019.util.BaseSchedulerProvider
import java.util.concurrent.TimeUnit
import kotlin.random.Random

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

    private val _visitors = MutableLiveData<Path>()
    val visitors: LiveData<Path>
        get() = _visitors

    private val _person = MutableLiveData<Path>()
    val person: LiveData<Path>
        get() = _person

    fun start() {
        clear()
        Single.timer(800, TimeUnit.MILLISECONDS)
            .repeat(10)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
            .doOnComplete {
                enter(isPerson = true)
            }
            .subscribe({
                enter(isPerson = false)
            }, { throwable ->
                logError(throwable)
            })
            .addDisposable()
    }

    private fun enter(
        age: Int = Random.nextInt(12, 81),
        ancient: Int = Random.nextInt(0, 101),
        medieval: Int = Random.nextInt(0, 101),
        modern: Int = Random.nextInt(0, 101),
        donation: Int = Random.nextInt(0, 101),
        painting: Int = Random.nextInt(0, 101),
        world: Int = Random.nextInt(0, 101),
        craft: Int = Random.nextInt(0, 101),
        isPerson: Boolean = false
    ) = userRepository.insert(
        age = age,
        ancient = ancient,
        medieval = medieval,
        modern = modern,
        donation = donation,
        painting = painting,
        world = world,
        craft = craft)
        .subscribeOn(scheduler.io())
        .observeOn(scheduler.mainThread())
        .also { if(isPerson) it.delaySubscription(2, TimeUnit.SECONDS) }
        .subscribe(object: SingleObserver<User> {
            override fun onSuccess(user: User) {
                updateCurrentLocation(user.id, isPerson = isPerson)
            }

            override fun onSubscribe(d: Disposable) {
                /* explicitly empty */
            }

            override fun onError(throwable: Throwable) {
                logError(throwable)
            }
        })

    private fun updateCurrentLocation(
        memberPk: Int,
        locationX: Int = Random.nextInt(0, 1200),
        locationY: Int = Random.nextInt(0, 700),
        isPerson: Boolean = false
    ) = userInfoRepository.updateCurrentLocation(
        memberPk = memberPk,
        locationX = locationX,
        locationY = locationY)
        .subscribeOn(scheduler.io())
        .observeOn(scheduler.mainThread())
        .subscribe(object: CompletableObserver {
            override fun onComplete() {
                recommendPath(memberPk = memberPk, isPerson = isPerson)
            }

            override fun onSubscribe(d: Disposable) {
                /* explicitly empty */
            }

            override fun onError(throwable: Throwable) {
                logError(throwable)
            }
        })

    private fun recommendPath(
        memberPk: Int,
        isPerson: Boolean = false
    ) =
        recommendRepository.getRecommend(memberPk = memberPk)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
            .subscribe(object: SingleObserver<List<Point>> {
                override fun onSuccess(points: List<Point>) {
                    Path(memberPk, points).let { path ->
                        if(isPerson)
                            _person.value = path
                        else
                            _visitors.value = path
                    }
                }

                override fun onSubscribe(d: Disposable) {
                    /* explicitly empty */
                }

                override fun onError(throwable: Throwable) {
                    logError(throwable)
                }
            })
}
