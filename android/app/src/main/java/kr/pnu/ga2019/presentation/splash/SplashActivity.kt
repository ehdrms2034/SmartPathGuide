/*
 * Created by Lee Oh Hyoung on 2020/06/03 .. 
 */
package kr.pnu.ga2019.presentation.splash

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import kr.pnu.ga2019.R
import kr.pnu.ga2019.databinding.ActivitySplashBinding
import kr.pnu.ga2019.presentation.base.BaseActivity
import kr.pnu.ga2019.presentation.user.UserPathActivity
import org.jetbrains.anko.toast

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(
    resourceId = R.layout.activity_splash
) {
    override val viewModel: SplashViewModel by viewModels()

    override fun observeLiveData() {
        viewModel.preferenceState.observe(this, Observer(::observeUiState))
    }

    override fun setListener() {
        binding.enterButton.setOnClickListener {
            viewModel.checkUserPreferenceExistence()
        }
    }

    override fun start() {
        viewModel.clearUserPreference()
    }

    private fun observeUiState(state: SplashUiState) {
        when(state) {
            is SplashUiState.Available -> {
                UserPathActivity.start(context = this, preference = state.preference)
            }
            is SplashUiState.Empty -> {
                toast(state.message)
                // TODO : Show Dialog
            }
        }
    }
}
