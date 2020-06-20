/*
 * Created by Lee Oh Hyoung on 2020/06/20 .. 
 */
package kr.pnu.ga2019.presentation.main

import android.content.Context
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import kr.pnu.ga2019.R
import kr.pnu.ga2019.databinding.ActivityMainBinding
import kr.pnu.ga2019.presentation.base.BaseActivity
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    resourceId = R.layout.activity_main
) {
    companion object {

        fun start(context: Context) {
            context.startActivity(
                context.intentFor<MainActivity>().singleTop()
            )
        }
    }

    override val viewModel: MainViewModel by viewModels()

    override fun setToolbar() {
        setSupportActionBar(binding.toolbar as? Toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        binding.toolbar.findViewById<TextView>(R.id.toolbar_title).setText(R.string.toolbar_main_activity)
    }

    override fun start() {
        /* explicitly empty */
    }

}