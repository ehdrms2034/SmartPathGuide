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
import kr.pnu.ga2019.domain.entity.User

class UserPathAdapter : ListAdapter<User, UserPathAdapter.UserPathViewHolder>(UserPathDiffUtil()) {

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

        fun bindTo(user: User) {
            binding.user = user
        }
    }

    private class UserPathDiffUtil : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem == newItem
    }

}

@BindingAdapter("setUser")
fun RecyclerView.setUser(list: List<User>?) =
    list?.let {
        val adapter: UserPathAdapter? = adapter as? UserPathAdapter
        adapter?.submitList(it)
    }
