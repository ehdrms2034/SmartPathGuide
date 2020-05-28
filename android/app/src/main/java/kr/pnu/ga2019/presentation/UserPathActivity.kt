package kr.pnu.ga2019.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kr.pnu.ga2019.R
import kr.pnu.ga2019.databinding.ActivityPathBinding

class UserPathActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPathBinding

    private val viewModel: UserPathViewModel by lazy {
        ViewModelProvider(this).get(UserPathViewModel::class.java)
    }

    private val userPathAdapter: UserPathAdapter by lazy {
        UserPathAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()
        setRecyclerView()
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_path)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun setRecyclerView() {
        with(binding.userPathRecyclerview) {
            layoutManager =
                LinearLayoutManager(this@UserPathActivity).apply {
                    reverseLayout = true
                    stackFromEnd = true
                }
            itemAnimator = DefaultItemAnimator()
            adapter = userPathAdapter
        }
    }
}
