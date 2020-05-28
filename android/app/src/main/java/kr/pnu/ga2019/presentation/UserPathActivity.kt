package kr.pnu.ga2019.presentation

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kr.pnu.ga2019.R
import kr.pnu.ga2019.databinding.ActivityPathBinding
import kr.pnu.ga2019.presentation.base.BaseActivity

class UserPathActivity : BaseActivity<ActivityPathBinding, UserPathViewModel>(
    resourceId = R.layout.activity_path
) {

    override val viewModel: UserPathViewModel by lazy {
        ViewModelProvider(this).get(UserPathViewModel::class.java)
    }

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun setRecyclerView() {
        with(binding.userPathRecyclerview) {
            layoutManager =
                LinearLayoutManager(this@UserPathActivity).apply {
                    reverseLayout = true
                    stackFromEnd = true
                }
            itemAnimator = DefaultItemAnimator()
            adapter = UserPathAdapter()
        }
    }

    override fun start() {
        viewModel.start()
    }

}
