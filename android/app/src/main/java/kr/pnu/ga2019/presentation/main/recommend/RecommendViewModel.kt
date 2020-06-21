/*
 * Created by Lee Oh Hyoung on 2020/06/21 .. 
 */
package kr.pnu.ga2019.presentation.main.recommend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kr.pnu.ga2019.data.repository.PlaceRepositoryImpl
import kr.pnu.ga2019.data.repository.RecommendRepositoryImpl
import kr.pnu.ga2019.domain.entity.Place
import kr.pnu.ga2019.domain.entity.RecommendedPlace
import kr.pnu.ga2019.domain.repository.PlaceRepository
import kr.pnu.ga2019.domain.repository.RecommendRepository
import kr.pnu.ga2019.presentation.base.BaseViewModel
import kr.pnu.ga2019.utility.AppSchedulerProvider
import kr.pnu.ga2019.utility.BaseSchedulerProvider
import kr.pnu.ga2019.utility.SingleLiveEvent
import java.util.concurrent.TimeUnit

class RecommendViewModel(
    private val placeRepository: PlaceRepository =
        PlaceRepositoryImpl(),
    private val recommendRepository: RecommendRepository =
        RecommendRepositoryImpl(),
    private val scheduler: BaseSchedulerProvider =
        AppSchedulerProvider()
) : BaseViewModel() {

    private val _places = SingleLiveEvent<List<Place>>()
    val places: LiveData<List<Place>> get() = _places

    private val _recommendResult = MutableLiveData<RecommendedPlace>()
    val recommendResult: LiveData<RecommendedPlace> get() = _recommendResult

    private val _finish = SingleLiveEvent<Any>()
    val finish: LiveData<Any> get() = _finish

    private val emptyRequestList: ArrayList<ArrayList<Any>> = makeEmptyPathList()
    var stayTime = MutableLiveData<Int>().apply { value = 0 }
    private var pathIndex: Int = 0
    private var timerDisposable: Disposable? = null

    private fun setStayTime(seconds: Long) {
        stayTime.value = seconds.toInt().plus(1)
        if(seconds == 30L) stopTimer() // 최대 관람 시간은 30분
    }

    fun startTimer(){
        timerDisposable = Observable.interval(1000L, TimeUnit.MILLISECONDS)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
            .subscribe(::setStayTime)
        timerDisposable?.addDisposable()
    }

    private fun stopTimer() {
        if(!timerDisposable!!.isDisposed) {
            timerDisposable!!.dispose()
            stayTime.value = 0
        }
    }

    // 박물관 각 지점의 정보를 가져옴
    fun getAllPlace() =
        placeRepository.getAllPlace()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
            .subscribe({
                _places.value = it
            }, { throwable ->

            })
            .addDisposable()

    // 다음 추천 지점을 받아옴
    fun getRecommend(currentPlaceId: Int) {
        if(pathIndex != 0){
            if(currentPlaceId == 1 || currentPlaceId == 8) {
                _finish.call()
                return
            }
        }
        stopTimer()
        updatePathList(pathIndex, currentPlaceId)
        recommendRepository.getRecommend(emptyRequestList)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
            .subscribe({ result ->
                _recommendResult.value = result
            }, { throwable ->

            })
            .addDisposable()

    }

    private fun updatePathList(seq: Int, placeId: Int){
        if(pathIndex < 12){
            emptyRequestList[pathIndex][0] = seq
            emptyRequestList[pathIndex][1] = placeId
            emptyRequestList[pathIndex][2] = stayTime.value!!
            pathIndex++
        } else {
            // 최대 12개 까지의 전시관만 관람가능
            _finish.call()
        }
    }

    private fun makeEmptyPathList(): ArrayList<ArrayList<Any>> {
        return ArrayList<ArrayList<Any>>().apply {
            add(arrayListOf(0, 0, 0))
            add(arrayListOf(0, 0, 0))
            add(arrayListOf(0, 0, 0))
            add(arrayListOf(0, 0, 0))
            add(arrayListOf(0, 0, 0))
            add(arrayListOf(0, 0, 0))
            add(arrayListOf(0, 0, 0))
            add(arrayListOf(0, 0, 0))
            add(arrayListOf(0, 0, 0))
            add(arrayListOf(0, 0, 0))
            add(arrayListOf(0, 0, 0))
            add(arrayListOf(0, 0, 0))
        }
    }
}
