/*
 * Created by Lee Oh Hyoung on 2020/06/03 .. 
 */
package kr.pnu.ga2019.presentation.splash

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kr.pnu.ga2019.R
import kr.pnu.ga2019.databinding.ActivitySplashBinding
import kr.pnu.ga2019.presentation.base.BaseActivity
import kr.pnu.ga2019.presentation.personal.PersonalPathActivity

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(
    resourceId = R.layout.activity_splash
) {
    override val viewModel: SplashViewModel by lazy {
        ViewModelProvider(this).get(SplashViewModel::class.java)
    }

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun observeLiveData() {
        viewModel.start.observe(this, Observer {
            PersonalPathActivity.start(this)
        })
    }

    override fun start() {
        /* explicitly empty */
    }
}
