/*
 * Created by Lee Oh Hyoung on 2020/06/22 .. 
 */
package kr.pnu.ga2019.presentation.main.intro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.pnu.ga2019.databinding.LayoutWatchMethodBinding
import kr.pnu.ga2019.utility.VisitorMethod

class IntroWatchMethodAdapter(
    private val visitorMethods: List<VisitorMethod>
) : RecyclerView.Adapter<IntroWatchMethodAdapter.WatchMethodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchMethodViewHolder {
        return WatchMethodViewHolder(
            LayoutWatchMethodBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = visitorMethods.size

    override fun onBindViewHolder(holder: WatchMethodViewHolder, position: Int) {
        holder.bindTo(visitorMethods[position])
    }

    inner class WatchMethodViewHolder(
        private val binding: LayoutWatchMethodBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(visitorMethods: VisitorMethod) {
            Glide.with(itemView.context)
                .load(visitorMethods.image)
                .into(binding.methodImageView)
            binding.methodDescription.text = visitorMethods.description
        }
    }
}
