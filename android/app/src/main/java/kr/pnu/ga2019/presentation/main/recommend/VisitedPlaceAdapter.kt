/*
 * Created by Lee Oh Hyoung on 2020/06/21 .. 
 */
package kr.pnu.ga2019.presentation.main.recommend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.pnu.ga2019.databinding.LayoutVisitedPlaceBinding
import kr.pnu.ga2019.domain.entity.Place
import kr.pnu.ga2019.utility.Const

class VisitedPlaceAdapter(
    private val onScrollWhenItemInserted: () -> Unit = {}
) : RecyclerView.Adapter<VisitedPlaceAdapter.VisitedPlaceViewHolder>() {

    private val places: ArrayList<Place> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitedPlaceViewHolder {
        return VisitedPlaceViewHolder(
            LayoutVisitedPlaceBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = places.size

    override fun onBindViewHolder(holder: VisitedPlaceViewHolder, position: Int) {
        holder.bindTo(place = places[position])
    }

    fun update(list: List<Place>) {
        places.clear()
        places.addAll(list)
        onScrollWhenItemInserted.invoke()
    }

    fun update(place: Place) {
        places.add(place)
        notifyItemRangeChanged(itemCount, places.size)
        onScrollWhenItemInserted.invoke()
    }

    fun getPlaces(): ArrayList<Place> = places

    inner class VisitedPlaceViewHolder(
        private val binding: LayoutVisitedPlaceBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(place: Place) {
            binding.place = place
            Glide.with(itemView.context)
                .load(Const.randomPointImage())
                .into(binding.pointImage)
        }
    }
}
