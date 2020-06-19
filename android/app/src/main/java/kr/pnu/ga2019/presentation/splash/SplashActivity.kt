/*
 * Created by Lee Oh Hyoung on 2020/06/03 .. 
 */
package kr.pnu.ga2019.presentation.splash

import android.graphics.Rect
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import kr.pnu.ga2019.R
import kr.pnu.ga2019.databinding.ActivitySplashBinding
import kr.pnu.ga2019.domain.entity.Museum
import kr.pnu.ga2019.domain.entity.Point
import kr.pnu.ga2019.presentation.base.BaseActivity
import kr.pnu.ga2019.presentation.dialog.PreferenceSettingDialog
import kr.pnu.ga2019.presentation.user.UserPathActivity
import kr.pnu.ga2019.utility.dpToPixel

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
        binding.pointsRecyclerview.addItemDecoration(object: RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position: Int = parent.getChildAdapterPosition(view)

                outRect.bottom = 10.dpToPixel()
                outRect.top = 12.dpToPixel()
                outRect.right = 10.dpToPixel()
                if(position == 0) { outRect.left = 16.dpToPixel() }
            }
        })
    }

    override fun start() {
        //viewModel.clearUserPreference()

        val museums = listOf(
            Museum( "국립중앙박물관", R.drawable.img_point_1),
            Museum( "국립춘천박물관", R.drawable.img_point_2),
            Museum( "국립청주박물관", R.drawable.img_point_3),
            Museum( "국립익산박물관", R.drawable.img_point_4),
            Museum( "국립대구박물관", R.drawable.img_point_5),
            Museum( "국립광주박물관", R.drawable.img_point_6)
        )
        pointAdapter.update(museums)
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
