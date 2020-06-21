/*
 * Created by Lee Oh Hyoung on 2020/06/21 .. 
 */
package kr.pnu.ga2019.presentation.main.intro

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kr.pnu.ga2019.R
import kr.pnu.ga2019.databinding.FragmentIntroBinding
import kr.pnu.ga2019.presentation.base.BaseFragment
import kr.pnu.ga2019.utility.Const
import kr.pnu.ga2019.utility.dpToPixel

class IntroFragment : BaseFragment<FragmentIntroBinding, IntroViewModel>(
    resourceId = R.layout.fragment_intro
) {

    companion object {

        fun newInstance(): IntroFragment {
            val args = Bundle()
            val fragment = IntroFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override val viewModel: IntroViewModel by viewModels()

    override fun start() {
        /* explicitly empty */
    }

    override fun setRecyclerView() {
        binding.imagePageRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = IntroImageAdapter(resources = Const.PAGE_IMAGES)
            addItemDecoration(object: RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    val itemPosition = parent.getChildAdapterPosition(view)
                    outRect.run {
                        top = 4.dpToPixel()
                        bottom = 4.dpToPixel()
                        when(itemPosition) {
                            0 -> {
                                left = 16.dpToPixel()
                                right = 4.dpToPixel()
                            }
                            state.itemCount -1 -> {
                                left = 4.dpToPixel()
                                right = 16.dpToPixel()
                            }
                            else -> {
                                left = 4.dpToPixel()
                                right = 4.dpToPixel()
                            }
                        }
                    }
                }
            })
            object: PagerSnapHelper() {}.attachToRecyclerView(this)
            smoothScrollToPosition(1)
        }

        binding.imagePointRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = IntroPointAdapter(resources = Const.MUSEUM_POINTS)
            addItemDecoration(object: RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    val itemPosition = parent.getChildAdapterPosition(view)
                    outRect.run {
                        top = 4.dpToPixel()
                        bottom = 4.dpToPixel()

                        when(itemPosition) {
                            0 -> {
                                left = 16.dpToPixel()
                                right = 2.dpToPixel()
                            }
                            state.itemCount - 1 -> {
                                left = 2.dpToPixel()
                                right = 16.dpToPixel()
                            }
                            else -> {
                                left = 2.dpToPixel()
                                right = 2.dpToPixel()
                            }
                        }
                    }
                }
            })
            object: PagerSnapHelper() {}.attachToRecyclerView(this)
        }
    }
}
