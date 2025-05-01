package com.bussiness.seniorcareapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bussiness.seniorcareapp.data.model.BannerData
import com.bussiness.seniorcareapp.databinding.ItemBannerBinding

class BannerAdapter(
    private val banners: List<BannerData>
) : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    inner class BannerViewHolder(val binding: ItemBannerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val item = banners[position]
        holder.binding.bannerImage.setImageResource(item.imageRes)
        holder.binding.descriptionText.text = item.description
    }

    override fun getItemCount(): Int = banners.size
}

