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
        viewModel.userPath.observe(this, Observer {
            val view: LayoutUserPointBinding = createUserPoint(it.memberPk)
            binding.mapRootLayout.addView(view.root)

            val path = Path().apply {
                moveTo(it.getMyLocation().locationX.toFloat(), it.getMyLocation().locationY.toFloat())
                it.getPointLocations().forEach { point ->
                    lineTo(point.locationX.toFloat(), point.locationY.toFloat())
                }
                close()
            }

            val animator = ObjectAnimator.ofFloat(view.root, View.X, View.Y, path)
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

    private fun createUserPoint(memberPk: Int): LayoutUserPointBinding =
        DataBindingUtil.inflate<LayoutUserPointBinding>(
            LayoutInflater.from(this),
            R.layout.layout_user_point,
            null,
            false
        ).apply {
            userId = memberPk.toString()
        }
}
