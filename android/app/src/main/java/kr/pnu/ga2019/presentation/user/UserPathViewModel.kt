/*
 * Created by Lee Oh Hyoung on 2020/05/26 .. 
 */
package kr.pnu.ga2019.presentation.user

import android.app.Application
import androidx.lifecycle.MutableLiveData
import io.reactivex.CompletableObserver
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import kr.pnu.ga2019.data.repository.*
import kr.pnu.ga2019.domain.entity.Path
import kr.pnu.ga2019.domain.entity.Point
import kr.pnu.ga2019.domain.entity.Preference
import kr.pnu.ga2019.domain.entity.User
import kr.pnu.ga2019.domain.repository.*
import kr.pnu.ga2019.presentation.base.BaseViewModel
import kr.pnu.ga2019.utility.AppSchedulerProvider
import kr.pnu.ga2019.utility.BaseSchedulerProvider
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class UserPathViewModel(
    application: Application,
    private val userRepository: UserRepository =
        UserRepositoryImpl(),
    private val userInfoRepository: UserInfoRepository =
        UserInfoRepositoryImpl(),
    private val pathRepository: PathRepository =
        PathRepositoryImpl(),
    private val recommendRepository: RecommendRepository =
        RecommendRepositoryImpl(),
    private val placeRepository: PlaceRepository =
        PlaceRepositoryImpl(),
    private val scheduler: BaseSchedulerProvider =
        AppSchedulerProvider()
) : BaseViewModel(application) {

    val uiState = MutableLiveData<UserPathUiState>()

    fun getAllPlace() =
        placeRepository.getAllPlace()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
            .subscribe({ list -> setPlaceState(list); start() },
                { throwable -> setErrorState(throwable) })
            .addDisposable()

    private fun start() {
        clear()
        showToast("Start")
        Single.timer(200L, TimeUnit.MILLISECONDS)
            .repeat(10)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
            .subscribe({ insertUser() },
                { throwable -> setErrorState(throwable) },
                { setVisitorSetState() })
            .addDisposable()
    }

    fun enterPersonalUser(preference: Preference) {
        userRepository.insert(
            age = preference.age,
            ancient = preference.ancient,
            medieval = preference.medieval,
            modern = preference.modern,
            donation = preference.donation,
            painting = preference.painting,
            world = preference.world,
            craft = preference.craft)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
            .subscribe(object: SingleObserver<User> {
                override fun onSuccess(user: User) {
                    updateCurrentLocation(user.id, isMyLocation = true)
                }

                override fun onSubscribe(d: Disposable) {
                    /* explicitly empty */
                }

                override fun onError(throwable: Throwable) {
                    setErrorState(throwable)
                }
            })
    }

    fun stop(){
        clear()
        showToast("Stop")
    }

    private fun insertUser(
        age: Int = Random.nextInt(12, 81),
        ancient: Int = Random.nextInt(0, 101),
        medieval: Int = Random.nextInt(0, 101),
        modern: Int = Random.nextInt(0, 101),
        donation: Int = Random.nextInt(0, 101),
        painting: Int = Random.nextInt(0, 101),
        world: Int = Random.nextInt(0, 101),
        craft: Int = Random.nextInt(0, 101)
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
        .subscribe(object: SingleObserver<User> {
            override fun onSuccess(user: User) {
                updateCurrentLocation(user.id)
            }

            override fun onSubscribe(d: Disposable) {
                /* explicitly empty */
            }

            override fun onError(throwable: Throwable) {
                setErrorState(throwable)
            }
        })

    private fun updateCurrentLocation(
        memberPk: Int,
        locationX: Int = Random.nextInt(0, 1200),
        locationY: Int = Random.nextInt(0, 700),
        isMyLocation: Boolean = false
    ) = userInfoRepository.updateCurrentLocation(
        memberPk = memberPk,
        locationX = locationX,
        locationY = locationY)
        .subscribeOn(scheduler.io())
        .observeOn(scheduler.mainThread())
        .subscribe(object: CompletableObserver {
            override fun onComplete() {
                recommendPath(memberPk = memberPk, isMyLocation = isMyLocation)
            }

            override fun onSubscribe(d: Disposable) {
                /* explicitly empty */
            }

            override fun onError(throwable: Throwable) {
                setErrorState(throwable)
            }
        })

    private fun recommendPath(memberPk: Int, isMyLocation: Boolean = false) =
        recommendRepository.getRecommend(memberPk = memberPk)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
            .subscribe(object: SingleObserver<List<Point>> {
                override fun onSuccess(points: List<Point>) {
                    if(isMyLocation)
                        setMyPathState(Path(memberPk, points))
                    else
                        setVisitorPathState(Path(memberPk, points))
                }

                override fun onSubscribe(d: Disposable) {
                    /* explicitly empty */
                }

                override fun onError(throwable: Throwable) {
                    setErrorState(throwable)
                }
            })
}
