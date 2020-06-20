package kr.pnu.ga2019.presentation.user

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Path
import android.view.View
import android.view.animation.Animation
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.orhanobut.logger.Logger
import kr.pnu.ga2019.R
import kr.pnu.ga2019.databinding.ActivityPathBinding
import kr.pnu.ga2019.databinding.LayoutMyLocationBinding
import kr.pnu.ga2019.databinding.LayoutPlacePinBinding
import kr.pnu.ga2019.databinding.LayoutUserPointBinding
import kr.pnu.ga2019.domain.entity.Preference
import kr.pnu.ga2019.presentation.base.BaseActivity
import kr.pnu.ga2019.utility.Const
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import kotlin.random.Random
import kr.pnu.ga2019.domain.entity.Path as UserPath

class UserPathActivity : BaseActivity<ActivityPathBinding, UserPathViewModel>(
    resourceId = R.layout.activity_path
) {
    companion object {
        private const val TAG: String = "UserPathActivity"
        private const val EXTRA_PREFERENCE: String = "preference"
        private const val FACTOR_WIDTH: Double = 0.8
        private const val FACTOR_HEIGHT: Int = 2

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
        viewModel.uiState.observe(this, Observer { state ->
            when(state) {
                UserPathUiState.SetVisitor -> {
                    binding.moveText.isEnabled = true
                    binding.moveButton.setOnClickListener { viewModel.enterPersonalUser(preference) }
                }
                is UserPathUiState.LoadVisitorPath -> {
                    createUserPoint().let { view ->
                        binding.mapRootLayout.addView(view.root)
                        setUserPointAnimation(view.root, state.path)
                    }
                }
                is UserPathUiState.LoadPlace -> {
                    state.places.forEach { place ->
                        val pin = LayoutPlacePinBinding.inflate(layoutInflater)
                        pin.place = place
                        pin.root.x = place.locationX.times(FACTOR_WIDTH).toFloat()
                        pin.root.y = place.locationY.times(FACTOR_HEIGHT).toFloat()
                        pin.root.setOnClickListener { toast(place.name) }
                        binding.mapRootLayout.addView(pin.root)
                    }
                }
                is UserPathUiState.LoadMyPath -> {
                    LayoutMyLocationBinding.inflate(layoutInflater).let { view ->
                        binding.mapRootLayout.addView(view.root)
                        view.root.x = state.path.getPointLocations().first().locationX.times(FACTOR_WIDTH).toFloat()
                        view.root.y = state.path.getPointLocations().first().locationY.times(FACTOR_HEIGHT).toFloat()
                        binding.moveText.text = getString(R.string.text_view_move_next_button)
                        binding.moveButton.setOnClickListener { moveNextPlace(state, view.root) }
                    }
                }
                is UserPathUiState.Error -> {
                    Logger.log(Logger.ERROR, TAG, state.throwable.message, state.throwable)
                }
            }
        })
    }

    override fun start() {
        viewModel.getAllPlace()
    }

    private fun moveNextPlace(state: UserPathUiState.LoadMyPath, view: View) {
        if(state.currentLocation != state.path.getPointLocations().count() - 1) {
            state.currentLocation++
            view.x = state.path.getPointLocations()[state.currentLocation].locationX.times(FACTOR_WIDTH).toFloat()
            view.y = state.path.getPointLocations()[state.currentLocation].locationY.times(FACTOR_HEIGHT).toFloat()
        } else {
            binding.moveText.text = getString(R.string.text_view_exit_button)
            binding.moveButton.setOnClickListener {
                toast(R.string.toast_exit_success)
                onBackPressed()
            }
        }
    }

    private fun createUserPoint(): LayoutUserPointBinding =
        LayoutUserPointBinding.inflate(layoutInflater).apply {
            userImage.setImageResource(Const.random())
        }

    private fun setUserPointAnimation(view: View, userPath: UserPath) {
        ObjectAnimator.ofFloat(view, View.X, View.Y, getUserPointPath(userPath)).apply {
            duration = Random.nextLong(120000L, 240000L)
            repeatCount = Animation.REVERSE
        }.start()
    }

    private fun getUserPointPath(userPath: UserPath): Path =
        Path().apply {
            moveTo(
                userPath.getPointLocations().first().locationX.times(FACTOR_WIDTH).toFloat(),
                userPath.getPointLocations().first().locationY.times(FACTOR_HEIGHT).toFloat()
            )

            userPath.getPointLocations().forEach { point ->
                lineTo(point.locationX.toFloat(), point.locationY.times(FACTOR_HEIGHT).toFloat())
            }
            close()
        }
}
