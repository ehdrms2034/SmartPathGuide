/*
 * Created by Lee Oh Hyoung on 2020/06/03 .. 
 */
package kr.pnu.ga2019.presentation.splash

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import kr.pnu.ga2019.R
import kr.pnu.ga2019.databinding.ActivitySplashBinding
import kr.pnu.ga2019.domain.entity.Point
import kr.pnu.ga2019.presentation.base.BaseActivity
import kr.pnu.ga2019.presentation.dialog.PreferenceSettingDialog
import kr.pnu.ga2019.presentation.user.UserPathActivity

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(
    resourceId = R.layout.activity_splash
) {
    companion object {
        private const val TAG: String = "SplashActivity"
    }

    override val viewModel: SplashViewModel by viewModels { SplashViewModelFactory(application) }
    private val pointAdapter: SplashPointAdapter by lazy { SplashPointAdapter() }

    override fun observeLiveData() {
        viewModel.uiState.observe(this, Observer(::observeUiState))
    }

    override fun setListener() {
        //binding.enterButton.setOnClickListener { viewModel.checkUserPreferenceExistence() }
    }

    override fun setRecyclerView() {
        binding.pointsRecyclerview.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.pointsRecyclerview.adapter = pointAdapter
        binding.pointsRecyclerview.itemAnimator = DefaultItemAnimator()
    }

    override fun start() {
        //viewModel.clearUserPreference()

        val points = listOf(
            Point(0, "국립전시관", 0.0, 0.0),
            Point(0, "국립전시관", 0.0, 0.0),
            Point(0, "국립전시관", 0.0, 0.0),
            Point(0, "국립전시관", 0.0, 0.0),
            Point(0, "국립전시관", 0.0, 0.0),
            Point(0, "국립전시관", 0.0, 0.0)
        )
        pointAdapter.update(points)
    }

    private fun observeUiState(state: SplashUiState) {
        when(state) {
            is SplashUiState.Available -> {
                UserPathActivity.start(context = this, preference = state.preference)
            }
            is SplashUiState.Empty -> {
                Logger.d(state.message)
                showPreferenceDialog()
            }
            is SplashUiState.Error -> {
                Logger.log(Logger.ERROR, TAG, state.throwable.message, state.throwable)
            }
        }
    }

    private fun showPreferenceDialog() {
        PreferenceSettingDialog(this) { preference ->
            viewModel.setUserPreference(preference)
        }.show()
    }
}
