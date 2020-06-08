package kr.pnu.ga2019.presentation.user

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Path
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import kr.pnu.ga2019.R
import kr.pnu.ga2019.databinding.ActivityPathBinding
import kr.pnu.ga2019.databinding.LayoutPlacePinBinding
import kr.pnu.ga2019.databinding.LayoutUserPointBinding
import kr.pnu.ga2019.domain.entity.Preference
import kr.pnu.ga2019.presentation.base.BaseActivity
import org.jetbrains.anko.intentFor
import kotlin.random.Random
import kr.pnu.ga2019.domain.entity.Path as UserPath

class UserPathActivity : BaseActivity<ActivityPathBinding, UserPathViewModel>(
    resourceId = R.layout.activity_path
) {
    companion object {
        private const val EXTRA_PREFERENCE: String = "preference"

        fun start(context: Context, preference: Preference) {
            context.startActivity(
                context.intentFor<UserPathActivity>(
                    EXTRA_PREFERENCE to preference
                )
            )
        }
    }

    override val viewModel: UserPathViewModel by viewModels()
    private val preference: Preference by lazy { intent.getSerializableExtra(EXTRA_PREFERENCE) as Preference }

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

        viewModel.places.observe(this, Observer { places ->
            places.forEach { place ->
                val pin = LayoutPlacePinBinding.inflate(layoutInflater)
                pin.place = place
                pin.root.x = place.locationX.times(0.8).toFloat()
                pin.root.y = place.locationY.times(2).toFloat()
                binding.mapRootLayout.addView(pin.root)
            }
        })
    }

    override fun setListener() {

    }

    override fun start() {
        viewModel.getAllPlace()
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
            duration = Random.nextLong(180000L, 360000L)
            repeatCount = Animation.REVERSE
        }.start()

    }

    private fun getUserPointPath(userPath: UserPath): Path =
        Path().apply {
            moveTo(
                userPath.getPointLocations().first().locationX.times(0.8).toFloat(),
                userPath.getPointLocations().first().locationY.times(2).toFloat()
            )

            userPath.getPointLocations().forEach { point ->
                lineTo(point.locationX.toFloat(), point.locationY.times(2).toFloat())
            }
            close()
        }
}
