/*
 * Created by Lee Oh Hyoung on 2020/06/21 .. 
 */
package kr.pnu.ga2019.presentation.main.recommend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.pnu.ga2019.data.repository.PlaceRepositoryImpl
import kr.pnu.ga2019.domain.entity.Place
import kr.pnu.ga2019.domain.repository.PlaceRepository
import kr.pnu.ga2019.presentation.base.BaseViewModel
import kr.pnu.ga2019.utility.AppSchedulerProvider
import kr.pnu.ga2019.utility.BaseSchedulerProvider

class RecommendViewModel(
    private val placeRepository: PlaceRepository =
        PlaceRepositoryImpl(),
    private val scheduler: BaseSchedulerProvider =
        AppSchedulerProvider()
) : BaseViewModel() {

    private val _places = MutableLiveData<List<Place>>()
    val places: LiveData<List<Place>> get() = _places

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
}
