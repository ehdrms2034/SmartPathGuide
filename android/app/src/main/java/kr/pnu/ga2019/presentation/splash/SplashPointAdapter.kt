/*
 * Created by Lee Oh Hyoung on 2020/06/20 .. 
 */
package kr.pnu.ga2019.presentation.splash

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.pnu.ga2019.databinding.LayoutSplashPointBinding
import kr.pnu.ga2019.domain.entity.Museum

class SplashPointAdapter(
    private val onClick: ((id: Int) -> Unit) = { /* explicitly empty */ }
) : RecyclerView.Adapter<SplashPointAdapter.PointViewHolder>() {

    private val points: ArrayList<Museum> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointViewHolder {
        return PointViewHolder(
            LayoutSplashPointBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = points.size

    override fun onBindViewHolder(holder: PointViewHolder, position: Int) {
        holder.bindTo(points[position])
    }

    fun update(list: List<Museum>) {
        points.clear()
        points.addAll(list)
        notifyDataSetChanged()
    }

    inner class PointViewHolder(
        private val binding: LayoutSplashPointBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(museum: Museum) {
            binding.root.setOnClickListener { onClick.invoke(museum.id) }
            binding.pointTitle.text = museum.name
            Glide.with(itemView.context)
                .load(museum.resId)
                .into(binding.pointImage)
        }
    }
}
