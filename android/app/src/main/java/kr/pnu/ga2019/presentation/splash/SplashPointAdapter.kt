/*
 * Created by Lee Oh Hyoung on 2020/06/20 .. 
 */
package kr.pnu.ga2019.presentation.splash

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.pnu.ga2019.databinding.LayoutSplashPointBinding
import kr.pnu.ga2019.domain.entity.Point

class SplashPointAdapter : RecyclerView.Adapter<SplashPointAdapter.PointViewHolder>() {

    private val points: ArrayList<Point> = arrayListOf()

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

    fun update(list: List<Point>) {
        points.clear()
        points.addAll(list)
        notifyDataSetChanged()
    }

    inner class PointViewHolder(
        private val binding: LayoutSplashPointBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(point: Point) {
            binding.pointTitle.text = point.name
        }
    }
}
