/*
 * Created by Lee Oh Hyoung on 2020/05/28 .. 
 */
package kr.pnu.ga2019.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.pnu.ga2019.R
import kr.pnu.ga2019.databinding.ItemUserBinding
import kr.pnu.ga2019.domain.entity.Path

class UserPathAdapter : ListAdapter<Path, UserPathAdapter.UserPathViewHolder>(PointDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPathViewHolder =
        UserPathViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_user, parent, false
            )
        )

    override fun onBindViewHolder(holder: UserPathViewHolder, position: Int): Unit =
        holder.bindTo(currentList[position])


    inner class UserPathViewHolder(
        private val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(path: Path) {
            binding.path = path
        }
    }

    private class PointDiffUtil : DiffUtil.ItemCallback<Path>() {
        override fun areItemsTheSame(oldItem: Path, newItem: Path): Boolean =
            oldItem.memberPk == newItem.memberPk

        override fun areContentsTheSame(oldItem: Path, newItem: Path): Boolean =
            oldItem == newItem
    }

}

@BindingAdapter("setUser")
fun RecyclerView.setUser(list: List<Path>?) =
    list?.let {
        val adapter: UserPathAdapter? = adapter as? UserPathAdapter
        adapter?.submitList(it)
    }
