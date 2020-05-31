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
import com.orhanobut.logger.Logger
import kr.pnu.ga2019.R
import kr.pnu.ga2019.databinding.ItemUserBinding
import kr.pnu.ga2019.domain.entity.Path

class UserPathAdapter : RecyclerView.Adapter<UserPathAdapter.UserPathViewHolder>() {

    private val userPathList: ArrayList<Path> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPathViewHolder =
        UserPathViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_user, parent, false
            )
        )

    override fun onBindViewHolder(holder: UserPathViewHolder, position: Int): Unit =
        holder.bindTo(userPathList[position])

    override fun getItemCount(): Int = userPathList.count()

    fun update(path: Path) {
        userPathList += path
        userPathList.sortBy { it.memberPk }
        notifyDataSetChanged()
    }

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

@BindingAdapter("setUserPath")
fun RecyclerView.setUserPath(path: Path?) =
    path?.let {
        val adapter: UserPathAdapter? = adapter as? UserPathAdapter
        adapter?.update(path)
    }
