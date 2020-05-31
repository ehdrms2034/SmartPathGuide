/*
 * Created by Lee Oh Hyoung on 2020/05/26 .. 
 */
package kr.pnu.ga2019.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
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

    companion object {
        private const val TAG: String = "UserPathViewModel"
    }

    private val _userPath = MutableLiveData<Path>()
    val userPath: LiveData<Path>
        get() = _userPath

    fun start() =
        stop().also {
            Single.timer(1, TimeUnit.SECONDS)
                .repeat()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.mainThread())
                .subscribe { insertUser() }
                .addDisposable()
                .also { showToast("Start") }
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
        locationX: Int = Random.nextInt(0, 1000),
        locationY: Int = Random.nextInt(0, 1000)
    ) = userInfoRepository.updateCurrentLocation(
        memberPk = memberPk,
        locationX = locationX,
        locationY = locationY)
        .subscribeOn(scheduler.io())
        .observeOn(scheduler.mainThread())
        .subscribe(object: CompletableObserver {
            override fun onComplete() {
                recommendPath(memberPk = memberPk)
            }

            override fun onSubscribe(d: Disposable) {
                /* explicitly empty */
            }

            override fun onError(throwable: Throwable) {
                logError(throwable)
            }
        })

    private fun recommendPath(memberPk: Int) =
        recommendRepository.getRecommend(memberPk = memberPk)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
            .subscribe(object: SingleObserver<List<Point>> {
                override fun onSuccess(points: List<Point>) {
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
