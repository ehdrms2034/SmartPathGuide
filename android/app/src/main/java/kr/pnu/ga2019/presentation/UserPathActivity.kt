package kr.pnu.ga2019.presentation

import android.animation.Animator
import android.animation.ObjectAnimator
import android.graphics.Path
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.pnu.ga2019.R
import kr.pnu.ga2019.databinding.ActivityPathBinding
import kr.pnu.ga2019.databinding.LayoutUserPointBinding
import kr.pnu.ga2019.presentation.base.BaseActivity
import kr.pnu.ga2019.domain.entity.Path as UserPath

class UserPathActivity : BaseActivity<ActivityPathBinding, UserPathViewModel>(
    resourceId = R.layout.activity_path
) {
    companion object {
        private const val ANIMATION_DURATION: Long = 20000L
    }

    override val viewModel: UserPathViewModel by lazy {
        ViewModelProvider(this).get(UserPathViewModel::class.java)
    }

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun observeLiveData() {
        viewModel.userPath.observe(this, Observer { userPath ->
            createUserPoint(userPath).let { view ->
                binding.mapRootLayout.addView(view.root)
                setUserPointAnimation(view.root, userPath)
            }
        })
    }

    override fun setRecyclerView() {
        with(binding.userPathRecyclerview) {
            layoutManager = LinearLayoutManager(
                this@UserPathActivity,
                RecyclerView.VERTICAL,
                false
            ).apply {
                stackFromEnd = true
            }
            itemAnimator = DefaultItemAnimator()
            adapter = UserPathAdapter {
                scrollToPosition(adapter?.itemCount!! - 1)
            }
        }
    }

    override fun start() {
        viewModel.start()
    }

    private fun createUserPoint(userPath: UserPath): LayoutUserPointBinding =
        DataBindingUtil.inflate<LayoutUserPointBinding>(
            LayoutInflater.from(this),
            R.layout.layout_user_point,
            null,
            false
        ).apply {
            userId = userPath.memberPk.toString()
        }

    private fun setUserPointAnimation(view: View, userPath: UserPath) {
        val animator = ObjectAnimator.ofFloat(view, View.X, View.Y, getUserPointPath(userPath))
        animator.duration = ANIMATION_DURATION
        animator.start()
        animator.addListener(object: Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                /* explicitly empty */
            }

            override fun onAnimationEnd(animation: Animator?) {
                val target: View? = animator.target as? View
                binding.mapRootLayout.removeView(target)
            }

            override fun onAnimationCancel(animation: Animator?) {
                /* explicitly empty */
            }

            override fun onAnimationStart(animation: Animator?) {
                /* explicitly empty */
            }
        })
    }

    private fun getUserPointPath(userPath: UserPath): Path =
        Path().apply {
            moveTo(userPath.getMyLocation().locationX.toFloat(), userPath.getMyLocation().locationY.toFloat())
            userPath.getPointLocations().forEach { point ->
                lineTo(point.locationX.toFloat(), point.locationY.toFloat())
            }
            close()
        }
}
