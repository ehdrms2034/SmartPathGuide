/*
 * Created by Lee Oh Hyoung on 2020/05/29 .. 
 */
package kr.pnu.ga2019.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import org.jetbrains.anko.toast

abstract class BaseActivity<T : ViewDataBinding, V: BaseViewModel>(
    private val resourceId: Int
) : AppCompatActivity() {

    abstract val viewModel: V

    protected lateinit var binding: T

    open fun bindViewModel() { /* explicitly empty */ }

    open fun setRecyclerView() { /* explicitly empty */ }

    open fun observeLiveData() { /* explicitly empty */ }

    open fun setListener() { /* explicitly empty */ }

    open fun setToolbar() { /* explicitly empty */ }

    abstract fun start()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()
        setToolbar()
        bindViewModel()
        observeLiveData()
        observeError()
        setListener()
        setRecyclerView()
        start()
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, resourceId)
        binding.lifecycleOwner = this
    }

    private fun observeError() {
        viewModel.showErrorMessage.observe(this, Observer { message ->
            message?.let { toast(message) }
        })
    }
}
