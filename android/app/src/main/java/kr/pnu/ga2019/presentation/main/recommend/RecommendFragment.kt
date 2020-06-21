/*
 * Created by Lee Oh Hyoung on 2020/06/21 .. 
 */
package kr.pnu.ga2019.presentation.main.recommend

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.orhanobut.logger.Logger
import com.shopgun.android.zoomlayout.ZoomLayout
import kr.pnu.ga2019.R
import kr.pnu.ga2019.databinding.FragmentRecommendBinding
import kr.pnu.ga2019.databinding.LayoutPlacePinBinding
import kr.pnu.ga2019.databinding.LayoutUserPointBinding
import kr.pnu.ga2019.presentation.base.BaseFragment
import kr.pnu.ga2019.utility.Const
import org.jetbrains.anko.support.v4.toast
import kotlin.random.Random

class RecommendFragment : BaseFragment<FragmentRecommendBinding, RecommendViewModel>(
    resourceId = R.layout.fragment_recommend
) {

    companion object {

        fun newInstance(): RecommendFragment {
            val args = Bundle()
            val fragment = RecommendFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val myLocation: LayoutUserPointBinding by lazy {
        createUserPoint().apply {
            root.x = (-450).toFloat()
            root.y = 1450.toFloat()
        }
    }

    private val pinViews: ArrayList<View> = arrayListOf()

    override val viewModel: RecommendViewModel by viewModels()

    override fun start() {
        binding.firstEnterMessage.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.shake))
        viewModel.getAllPlace()

        binding.pinchZoomZoomLayout.addOnZoomListener(object: ZoomLayout.OnZoomListener {
            override fun onZoomBegin(view: ZoomLayout?, scale: Float) {
            }

            override fun onZoom(view: ZoomLayout?, scale: Float) {
                // Default : 1.0f, Max : 3.0f
                Logger.d("zoom scale: $scale")
            }

            override fun onZoomEnd(view: ZoomLayout?, scale: Float) {
            }
        })
    }

    override fun observeLiveData() {
        super.observeLiveData()
        viewModel.places.observe(viewLifecycleOwner, Observer { places ->
            places?.forEach { place ->
                val pin = LayoutPlacePinBinding.inflate(layoutInflater)
                pin.place = place

                // x: -400 ~ +400
                // y: +100 ~ +1600
//                pin.root.x = place.locationX.toFloat()
//                pin.root.y = place.locationY.toFloat()
                pin.root.x = Random.nextInt(-400, 400).toFloat()
                pin.root.y = Random.nextInt(100, 1600).toFloat()
                pin.root.setOnClickListener { toast(place.name) }

                pinViews.add(pin.root)
                binding.pinchZoomZoomLayout.addView(pin.root)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        pinViews.forEach { view -> binding.pinchZoomZoomLayout.addView(view) }
        binding.pinchZoomZoomLayout.addView(myLocation.root)
    }

    override fun onPause() {
        super.onPause()
        pinViews.forEach { view -> binding.pinchZoomZoomLayout.removeView(view) }
        binding.pinchZoomZoomLayout.removeView(myLocation.root)

    }

    private fun createUserPoint(): LayoutUserPointBinding =
        LayoutUserPointBinding.inflate(layoutInflater).apply {
            userImage.setImageResource(Const.random())
        }
}