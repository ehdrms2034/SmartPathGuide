/*
 * Created by Lee Oh Hyoung on 2020/06/01 .. 
 */
package kr.pnu.ga2019.presentation.personal

import androidx.lifecycle.ViewModelProvider
import kr.pnu.ga2019.R
import kr.pnu.ga2019.databinding.ActivityPersonalBinding
import kr.pnu.ga2019.presentation.base.BaseActivity

class PersonalPathActivity : BaseActivity<ActivityPersonalBinding, PersonalPathViewModel>(
    resourceId = R.layout.activity_personal
) {
    override val viewModel: PersonalPathViewModel by lazy {
        ViewModelProvider(this).get(PersonalPathViewModel::class.java)
    }

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun start() {
        /* explicitly empty */
    }
}
