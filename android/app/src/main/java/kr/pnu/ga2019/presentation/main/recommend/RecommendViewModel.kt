/*
 * Created by Lee Oh Hyoung on 2020/06/21 .. 
 */
package kr.pnu.ga2019.presentation.main.recommend

import androidx.lifecycle.LiveData
import kr.pnu.ga2019.data.repository.PlaceRepositoryImpl
import kr.pnu.ga2019.data.repository.RecommendRepositoryImpl
import kr.pnu.ga2019.domain.entity.PathData
import kr.pnu.ga2019.domain.entity.Place
import kr.pnu.ga2019.domain.repository.PlaceRepository
import kr.pnu.ga2019.domain.repository.RecommendRepository
import kr.pnu.ga2019.presentation.base.BaseViewModel
import kr.pnu.ga2019.utility.AppSchedulerProvider
import kr.pnu.ga2019.utility.BaseSchedulerProvider
import kr.pnu.ga2019.utility.SingleLiveEvent

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

    private val emptyRequestList: ArrayList<ArrayList<Any>> = makeEmptyPathList()

    init {
        getRecommend()
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
    fun getRecommend() {
        val request =
        recommendRepository.getRecommend(emptyRequestList)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
            .subscribe({

            }, {

            })
            .addDisposable()

    }

    fun updatePathList(index: Int, seq: Int, placeId: Int, stayTime: Int){
        emptyRequestList[index][0] = seq
        emptyRequestList[index][1] = placeId
        emptyRequestList[index][2] = stayTime
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