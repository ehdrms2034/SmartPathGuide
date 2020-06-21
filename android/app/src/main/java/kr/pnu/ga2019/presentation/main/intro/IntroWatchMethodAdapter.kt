/*
 * Created by Lee Oh Hyoung on 2020/06/22 .. 
 */
package kr.pnu.ga2019.presentation.main.intro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.pnu.ga2019.databinding.LayoutWatchMethodBinding

class IntroWatchMethodAdapter(
    private val descriptions: List<String>
) : RecyclerView.Adapter<IntroWatchMethodAdapter.WatchMethodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchMethodViewHolder {
        return WatchMethodViewHolder(
            LayoutWatchMethodBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = descriptions.size

    override fun onBindViewHolder(holder: WatchMethodViewHolder, position: Int) {
        holder.bindTo(descriptions[position])
    }

    inner class WatchMethodViewHolder(
        private val binding: LayoutWatchMethodBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(text: String) {
            binding.methodDescription.text = text
        }
    }
}
