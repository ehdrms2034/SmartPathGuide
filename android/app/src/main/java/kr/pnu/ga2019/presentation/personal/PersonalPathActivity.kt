/*
 * Created by Lee Oh Hyoung on 2020/06/01 .. 
 */
package kr.pnu.ga2019.presentation.personal

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorInt
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.pnu.ga2019.R
import kr.pnu.ga2019.databinding.ActivityPersonalBinding
import kr.pnu.ga2019.databinding.LayoutUserPointBinding
import kr.pnu.ga2019.presentation.adapter.UserPathAdapter
import kr.pnu.ga2019.presentation.base.BaseActivity
import kr.pnu.ga2019.util.PointAnimator
import kr.pnu.ga2019.domain.entity.Path as UserPath

class PersonalPathActivity : BaseActivity<ActivityPersonalBinding, PersonalPathViewModel>(
    resourceId = R.layout.activity_personal
) {

    companion object {
        private const val ANIMATION_DURATION: Long = 40000L
    }

    override val viewModel: PersonalPathViewModel by lazy {
        ViewModelProvider(this).get(PersonalPathViewModel::class.java)
    }

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun observeLiveData() {
        viewModel.visitors.observe(this, Observer { userPath ->
            createUserPoint(userPath).let { view ->
                binding.imageViewPoint.addView(PathLine(
                    context = this@PersonalPathActivity,
                    width = 2f,
                    strokeColor = Color.GRAY,
                    userPath = userPath
                ))
                binding.imageViewPoint.addView(view.root)
                setUserPointAnimation(view.root, userPath)
            }
        })

        viewModel.person.observe(this, Observer { userPath ->
            createUserPoint(userPath).let { view ->
                view.userPoint.setImageResource(R.drawable.drawable_user_point_blue)
                binding.imageViewPoint.addView(PathLine(
                    context = this@PersonalPathActivity,
                    width = 6f,
                    strokeColor = Color.GREEN,
                    userPath = userPath
                ))
                binding.imageViewPoint.addView(view.root)
                setUserPointAnimation(view.root, userPath)
            }
        })

        viewModel.restart.observe(this, Observer {
            binding.imageViewPoint.removeAllViews()
        })
    }

    override fun setRecyclerView() {
        with(binding.userPathRecyclerview) {
            layoutManager = LinearLayoutManager(
                this@PersonalPathActivity,
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
        ObjectAnimator.ofFloat(view, View.X, View.Y, getUserPointPath(userPath)).apply {
            duration = ANIMATION_DURATION
            addListener(object: PointAnimator() {
                override fun onAnimationEnd(animation: Animator) {
                    //val target: View? = target as? View
                    //binding.imageViewPoint.removeView(target)
                }
            })
        }.start()
    }

    private fun getUserPointPath(userPath: UserPath): Path =
        Path().apply {
            moveTo(userPath.getMyLocation().locationX.toFloat(), userPath.getMyLocation().locationY.toFloat())
            userPath.getPointLocations().forEach { point ->
                lineTo(point.locationX.toFloat(), point.locationY.toFloat())
            }
            close()
        }

    private class PathLine(
        context: Context,
        private val width: Float,
        @ColorInt private val strokeColor: Int,
        private val userPath: UserPath
    ) : View(context) {

        private val paint: Paint = Paint().apply {
            strokeWidth = width
            color = strokeColor
        }

        override fun onDraw(canvas: Canvas) {
            with(userPath.path) {
                forEachIndexed { index, point ->
                    if(index != size - 1) {
                        canvas.drawLine(
                            point.locationX.toFloat(),
                            point.locationY.toFloat(),
                            get(index + 1).locationX.toFloat(),
                            get(index + 1).locationY.toFloat(),
                            paint
                        )
                    }
                }
            }
        }
    }
}
