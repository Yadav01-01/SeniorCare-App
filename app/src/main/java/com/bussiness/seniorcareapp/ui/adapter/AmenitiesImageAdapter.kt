package com.bussiness.seniorcareapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bussiness.seniorcareapp.data.model.AmenitiesImage
import com.bussiness.seniorcareapp.databinding.ItemAmenitiesImagesBinding

class AmenitiesImageAdapter(
    private val posters: List<AmenitiesImage>
) : RecyclerView.Adapter<AmenitiesImageAdapter.PosterViewHolder>() {

    inner class PosterViewHolder(private val binding: ItemAmenitiesImagesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(poster: AmenitiesImage) {
            binding.imageView.setImageResource(poster.imageResId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        val binding = ItemAmenitiesImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PosterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        holder.bind(posters[position])
    }

    override fun getItemCount(): Int = posters.size
}
