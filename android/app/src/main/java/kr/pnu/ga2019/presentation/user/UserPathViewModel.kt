/*
 * Created by Lee Oh Hyoung on 2020/05/26 .. 
 */
package kr.pnu.ga2019.presentation.user

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.CompletableObserver
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import kr.pnu.ga2019.data.repository.*
import kr.pnu.ga2019.domain.entity.*
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

    private val _userPath = MutableLiveData<Path>()
    val userPath: LiveData<Path>
        get() = _userPath

    private val _myPath = MutableLiveData<Path>()
    val myPath: LiveData<Path>
        get() = _myPath

    val places = MutableLiveData<List<Place>>()

    fun getAllPlace() =
        placeRepository.getAllPlace()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
            .subscribe({ list ->
                places.value = list
                start()
            }, { throwable ->
                logError(throwable)
            })
            .addDisposable()

    private fun start() {
        clear()
        showToast("Start")
        Single.timer(200L, TimeUnit.MILLISECONDS)
            .repeat(10)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
            .subscribe { insertUser() }
            .addDisposable()
    }

    private fun enterPersonalUser(preference: Preference) {
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
                    logError(throwable)
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
                logError(throwable)
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
                logError(throwable)
            }
        })

    private fun recommendPath(memberPk: Int, isMyLocation: Boolean = false) =
        recommendRepository.getRecommend(memberPk = memberPk)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
            .subscribe(object: SingleObserver<List<Point>> {
                override fun onSuccess(points: List<Point>) {
                    if(isMyLocation)
                        _myPath.value = Path(memberPk, points)
                    else
                        _userPath.value = Path(memberPk, points)
                }

                override fun onSubscribe(d: Disposable) {
                    /* explicitly empty */
                }

                override fun onError(throwable: Throwable) {
                    logError(throwable)
                }
            })
}
