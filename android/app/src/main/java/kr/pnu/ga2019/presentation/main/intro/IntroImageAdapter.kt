/*
 * Created by Lee Oh Hyoung on 2020/06/21 .. 
 */
package kr.pnu.ga2019.presentation.main.intro

import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.pnu.ga2019.databinding.LayoutIntroPageImageBinding
import kr.pnu.ga2019.utility.dpToPixel
import org.jetbrains.anko.windowManager

class IntroImageAdapter(
    private val resources: List<Int>
) : RecyclerView.Adapter<IntroImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutIntroPageImageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = resources.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindTo(resources[position])
    }

    inner class ImageViewHolder(
        private val binding: LayoutIntroPageImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val displayMetrics = DisplayMetrics()

        fun bindTo(resId: Int) {
            Glide.with(itemView.context)
                .load(resId)
                .into(binding.pageImageView)

            itemView.context.windowManager.defaultDisplay.getMetrics(displayMetrics)
            itemView.layoutParams.width = (displayMetrics.widthPixels.toDouble() - 32.dpToPixel()).toInt()
        }
    }
}