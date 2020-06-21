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
        recommendRepository.getRecommend(makePathData())
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
            .subscribe({

            }, {

            })
            .addDisposable()

    }

    private fun makePathData(): List<List<Any>> {
        return ArrayList<List<Any>>().apply {
            add(listOf(0, 1, 10))
            add(listOf(1, 2, 10))
            add(listOf(2, 3, 12))
            add(listOf(0, 0, 0))
            add(listOf(0, 0, 0))
            add(listOf(0, 0, 0))
            add(listOf(0, 0, 0))
            add(listOf(0, 0, 0))
            add(listOf(0, 0, 0))
            add(listOf(0, 0, 0))
            add(listOf(0, 0, 0))
            add(listOf(0, 0, 0))
        }
    }
}
