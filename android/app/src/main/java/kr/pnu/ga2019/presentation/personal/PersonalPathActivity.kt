/*
 * Created by Lee Oh Hyoung on 2020/06/01 .. 
 */
package kr.pnu.ga2019.presentation.personal

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Path
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.orhanobut.logger.Logger
import kr.pnu.ga2019.R
import kr.pnu.ga2019.databinding.ActivityPersonalBinding
import kr.pnu.ga2019.databinding.LayoutUserPointBinding
import kr.pnu.ga2019.presentation.base.BaseActivity
import org.jetbrains.anko.displayMetrics
import org.jetbrains.anko.intentFor
import kotlin.random.Random
import kr.pnu.ga2019.domain.entity.Path as UserPath

class PersonalPathActivity : BaseActivity<ActivityPersonalBinding, PersonalPathViewModel>(
    resourceId = R.layout.activity_personal
) {

    companion object {

        fun start(context: Context) {
            context.startActivity(
                context.intentFor<PersonalPathActivity>()
            )
        }
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
                binding.mapRootLayout.addView(view.root)
                setUserPointAnimation(view.root, userPath)
            }
        })

        viewModel.person.observe(this, Observer { userPath ->
            createUserPoint(userPath).let { view ->
                binding.mapRootLayout.addView(view.root)
                setUserPointAnimation(view.root, userPath)
            }
        })
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
            duration = Random.nextLong(160000L, 240000L)
            repeatCount = Animation.REVERSE
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

}
