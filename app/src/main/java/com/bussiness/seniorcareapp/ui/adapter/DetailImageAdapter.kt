package com.bussiness.seniorcareapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bussiness.seniorcareapp.data.model.SmallImageItem
import com.bussiness.seniorcareapp.databinding.ItemImageBinding

class DetailImageAdapter(
    private val items: List<SmallImageItem>
) : RecyclerView.Adapter<DetailImageAdapter.SmallImageViewHolder>() {

    inner class SmallImageViewHolder(val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallImageViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SmallImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SmallImageViewHolder, position: Int) {
        val item = items[position]

        holder.binding.smallImage.setImageResource(item.imageResId)
    }

    override fun getItemCount(): Int = items.size
}
