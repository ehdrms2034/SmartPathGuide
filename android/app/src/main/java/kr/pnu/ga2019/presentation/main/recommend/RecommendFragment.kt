/*
 * Created by Lee Oh Hyoung on 2020/06/21 .. 
 */
package kr.pnu.ga2019.presentation.main.recommend

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.pnu.ga2019.R
import kr.pnu.ga2019.databinding.FragmentRecommendBinding
import kr.pnu.ga2019.databinding.LayoutPlacePinBinding
import kr.pnu.ga2019.databinding.LayoutUserPointBinding
import kr.pnu.ga2019.domain.entity.Place
import kr.pnu.ga2019.presentation.base.BaseFragment
import kr.pnu.ga2019.utility.Const
import kr.pnu.ga2019.utility.dpToPixel
import org.jetbrains.anko.support.v4.toast
import kotlin.random.Random

class RecommendFragment : BaseFragment<FragmentRecommendBinding, RecommendViewModel>(
    resourceId = R.layout.fragment_recommend
) {

    companion object {

        private const val MOVE_ANIMATION_DURATION: Long = 2500L

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
    private val cachedPlaces: ArrayList<Place> = arrayListOf()
    private var isSelectMoved: Boolean = false

    override val viewModel: RecommendViewModel by viewModels()
    private val visitedPlaceAdapter: VisitedPlaceAdapter by lazy {
        VisitedPlaceAdapter {
            binding.pathRecyclerView.scrollToPosition(
                binding.pathRecyclerView.adapter?.itemCount!! - 1
            )
        }
    }

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun start() {
        binding.firstEnterMessage.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.shake))
        viewModel.getAllPlace()
    }

    override fun setRecyclerView() {
        binding.pathRecyclerView.run {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false).apply { stackFromEnd = true }
            adapter = visitedPlaceAdapter
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(object: RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    val position = parent.getChildAdapterPosition(view)
                    if(position != 0) {
                        outRect.left = 4.dpToPixel()
                        outRect.right = 4.dpToPixel()
                    }
                    if(position == state.itemCount - 1) {
                        outRect.right = 8.dpToPixel()
                    }
                }
            })
        }
    }

    override fun observeLiveData() {
        super.observeLiveData()
        viewModel.places.observe(viewLifecycleOwner, Observer { places ->
            cachedPlaces.clear()
            setMoveButtonClickListener()

            places?.forEach { place ->
                val pin = LayoutPlacePinBinding.inflate(layoutInflater)
                pin.place = place

                // x: -400 ~ +400
                // y: +100 ~ +1600
//                pin.root.x = place.locationX.toFloat()
//                pin.root.y = place.locationY.toFloat()

                // 임의로 위치를 넣어줌 ... 향후 서버에서 수정 필요함
                val randomX: Int = Random.nextInt(-400, 400)
                val randomY: Int = Random.nextInt(100, 1600)

                pin.root.x = randomX.toFloat()
                pin.root.y = randomY.toFloat()
                place.locationX = randomX
                place.locationY = randomY
                cachedPlaces.add(place)

                pinViews.add(pin.root)
                binding.pinchZoomLayout.addView(pin.root)
            }
        })

        viewModel.recommendResult.observe(viewLifecycleOwner, Observer {
            it?.let { result ->
                cachedPlaces.first { place -> place.id == result.first.placeId }
                    .let { currentPlace ->
                        toast("${currentPlace.name}으로 이동하세요")
                        Log.d("currentPlace", "현재 위치 : ${currentPlace.name}, x: ${currentPlace.locationX}, y: ${currentPlace.locationY}")
                        viewModel.startTimer()
                        binding.currentPlace = currentPlace

                        val moveX: ObjectAnimator = ObjectAnimator.ofFloat(myLocation.root, "x", currentPlace.locationX.toFloat())
                        val moveY: ObjectAnimator = ObjectAnimator.ofFloat(myLocation.root, "y", currentPlace.locationY.toFloat())

                        val animatorSet = AnimatorSet()
                        animatorSet.playTogether(moveX, moveY)
                        animatorSet.duration = MOVE_ANIMATION_DURATION
                        animatorSet.start()
                        visitedPlaceAdapter.update(currentPlace)
                    }

            }
        })

        viewModel.finish.observe(viewLifecycleOwner, Observer {
            showExitDialog()
        })
    }

    override fun onResume() {
        super.onResume()
        pinViews.forEach { view -> binding.pinchZoomLayout.addView(view) }
        binding.pinchZoomLayout.addView(myLocation.root)
        if(isSelectMoved) {
            binding.firstEnterMessage.visibility = View.GONE
        }
    }

    override fun onPause() {
        super.onPause()
        pinViews.forEach { view -> binding.pinchZoomLayout.removeView(view) }
        binding.pinchZoomLayout.removeView(myLocation.root)

    }

    private fun showExitDialog() {
        viewModel.stopTimer()
        ExitDialog(requireActivity()) {
            activity?.onBackPressed()
        }.show()
    }

    private fun setMoveButtonClickListener() {
        binding.moveAncientButton.setOnClickListener {
            toast("고대전시관으로 이동하세요")
            binding.firstEnterMessage.animation = null
            binding.firstEnterMessage.visibility = View.GONE
            if(pinViews.isNotEmpty()) {

                val moveX: ObjectAnimator = ObjectAnimator.ofFloat(myLocation.root, "x", pinViews[0].x)
                val moveY: ObjectAnimator = ObjectAnimator.ofFloat(myLocation.root, "y", pinViews[0].y)

                val animatorSet = AnimatorSet()
                animatorSet.playTogether(moveX, moveY)
                animatorSet.duration = MOVE_ANIMATION_DURATION
                animatorSet.start()


                isSelectMoved = true
                binding.currentPlace = cachedPlaces[0]
                visitedPlaceAdapter.update(cachedPlaces[0])
                viewModel.startTimer()
                binding.currentState.visibility = View.VISIBLE
            }
        }
        binding.moveScienceButton.setOnClickListener {
            toast("과학전시관으로 이동하세요")
            binding.firstEnterMessage.animation = null
            binding.firstEnterMessage.visibility = View.GONE
            if(pinViews.isNotEmpty()) {

                val moveX: ObjectAnimator = ObjectAnimator.ofFloat(myLocation.root, "x", pinViews[7].x)
                val moveY: ObjectAnimator = ObjectAnimator.ofFloat(myLocation.root, "y", pinViews[7].y)

                val animatorSet = AnimatorSet()
                animatorSet.playTogether(moveX, moveY)
                animatorSet.duration = MOVE_ANIMATION_DURATION
                animatorSet.start()

                isSelectMoved = true
                binding.currentPlace = cachedPlaces[7]
                visitedPlaceAdapter.update(cachedPlaces[7])
                viewModel.startTimer()
                binding.currentState.visibility = View.VISIBLE
            }
        }
    }

    private fun createUserPoint(): LayoutUserPointBinding =
        LayoutUserPointBinding.inflate(layoutInflater).apply {
            userImage.setImageResource(Const.random())
        }
}