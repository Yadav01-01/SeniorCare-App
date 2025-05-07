package com.bussiness.seniorcareapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bussiness.seniorcareapp.data.model.PosterItem
import com.bussiness.seniorcareapp.databinding.ItemExFacilitiesBinding

class ExFacilitiesAdapter(
    private val items: List<PosterItem>,private val listener: (PosterItem) -> Unit
) : RecyclerView.Adapter<ExFacilitiesAdapter.PosterViewHolder>() {

    inner class PosterViewHolder(val binding: ItemExFacilitiesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        val binding = ItemExFacilitiesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PosterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            posterImage.setImageResource(item.imageResId)
            titleText.text = item.title
            descriptionText.text = item.description
            root.setOnClickListener {
                listener(item)
            }

        }
    }

    override fun getItemCount(): Int = items.size
}
