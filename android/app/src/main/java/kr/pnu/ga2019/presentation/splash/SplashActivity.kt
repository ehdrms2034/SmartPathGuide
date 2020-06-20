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
import kr.pnu.ga2019.presentation.base.BaseActivity
import kr.pnu.ga2019.presentation.main.MainActivity
import kr.pnu.ga2019.presentation.user.UserPathActivity
import kr.pnu.ga2019.utility.Const
import kr.pnu.ga2019.utility.dpToPixel
import org.jetbrains.anko.toast

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(
    resourceId = R.layout.activity_splash
) {
    companion object {
        private const val TAG: String = "SplashActivity"
    }

    override val viewModel: SplashViewModel by viewModels { SplashViewModelFactory(application) }
    private val pointAdapter: SplashPointAdapter by lazy {
        SplashPointAdapter { museum ->
            //TODO : Dialog for Testing
            toast("id: ${museum.id}, name: ${museum.name}")
        }
    }

    override fun observeLiveData() {
        viewModel.uiState.observe(this, Observer(::observeUiState))
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
                outRect.right = if(position == state.itemCount -1) 16.dpToPixel() else 10.dpToPixel()
                if(position == 0) { outRect.left = 16.dpToPixel() }
            }
        })
    }

    override fun setListener() {
        binding.enterButton.setOnClickListener { MainActivity.start(this) }
    }

    override fun start() {
        pointAdapter.update(Const.MUSEUM_POINTS)
    }

    private fun observeUiState(state: SplashUiState) {
        when(state) {
            is SplashUiState.Available -> {
                UserPathActivity.start(context = this, preference = state.preference)
            }
            is SplashUiState.Empty -> {
                Logger.d(state.message)
            }
            is SplashUiState.Error -> {
                Logger.log(Logger.ERROR, TAG, state.throwable.message, state.throwable)
            }
        }
    }
}
