/*
 * Created by Lee Oh Hyoung on 2020/06/21 .. 
 */
package kr.pnu.ga2019.presentation.main.intro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.pnu.ga2019.databinding.LayoutIntroPointImageBinding
import kr.pnu.ga2019.domain.entity.Museum

class IntroPointAdapter(
    private val resources: List<Museum>
) : RecyclerView.Adapter<IntroPointAdapter.IntroPointViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroPointViewHolder {
        return IntroPointViewHolder(
            LayoutIntroPointImageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = resources.size

    override fun onBindViewHolder(holder: IntroPointViewHolder, position: Int) {
        holder.bindTo(museum = resources[position])
    }

    inner class IntroPointViewHolder(
        private val binding: LayoutIntroPointImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(museum: Museum) {
            binding.title = museum.name
            Glide.with(itemView.context)
                .load(museum.resId)
                .into(binding.pointImageView)
        }
    }
}
