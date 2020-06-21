/*
 * Created by Lee Oh Hyoung on 2020/06/20 .. 
 */
package kr.pnu.ga2019.presentation.main

import android.content.Context
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import kr.pnu.ga2019.R
import kr.pnu.ga2019.databinding.ActivityMainBinding
import kr.pnu.ga2019.presentation.base.BaseActivity
import kr.pnu.ga2019.presentation.base.BaseFragment
import kr.pnu.ga2019.presentation.main.intro.IntroFragment
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

    enum class TabPosition() {
        INTRO,
        RECOMMEND
    }

    private val fragments: List<Fragment> = listOf(
        IntroFragment.newInstance(),
        IntroFragment.newInstance()
    )

    override val viewModel: MainViewModel by viewModels()

    override fun setToolbar() {
        setSupportActionBar(binding.toolbar as? Toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        binding.toolbar.findViewById<TextView>(R.id.toolbar_title).setText(R.string.toolbar_main_activity)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return false
    }

    override fun start() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragments[0])
            .commitAllowingStateLoss()

        setTabLayout()
    }

    private fun setTabLayout() {
        binding.tabLayout.apply {
            addTab(newTab().setIcon(R.drawable.img_musium))
            addTab(newTab().setIcon(R.drawable.img_musium))
            tabGravity = TabLayout.GRAVITY_FILL

            addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab) { /* explicitly empty */ }
                override fun onTabUnselected(tab: TabLayout.Tab) { /* explicitly empty */ }
                override fun onTabSelected(tab: TabLayout.Tab) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragments[tab.position])
                        .commit()
                }
            })
        }
    }

}