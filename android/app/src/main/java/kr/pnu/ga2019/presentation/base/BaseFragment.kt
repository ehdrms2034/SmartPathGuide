/*
 * Created by Lee Oh Hyoung on 2020/06/21 .. 
 */
package kr.pnu.ga2019.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : ViewDataBinding, V: BaseViewModel>(
    private val resourceId: Int
) : Fragment() {

    abstract val viewModel: V

    protected lateinit var binding: T

    open fun bindViewModel() { /* explicitly empty */ }

    open fun setRecyclerView() { /* explicitly empty */ }

    open fun observeLiveData() { /* explicitly empty */ }

    open fun setListener() { /* explicitly empty */ }

    abstract fun start()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setBinding()
        bindViewModel()
        setListener()
        setRecyclerView()
        start()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeLiveData()
    }

    private fun setBinding() {
        binding = DataBindingUtil.inflate(layoutInflater, resourceId, null, false)
        binding.lifecycleOwner = viewLifecycleOwner
    }
}