package com.bussiness.seniorcareapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.bussiness.seniorcareapp.data.model.Facility
import com.bussiness.seniorcareapp.databinding.ItemSavedFacilitiesBinding

class FacilityListingAdapter(
    private val facilityList: List<Facility>,
    private val onItemClick: (Facility) -> Unit,
    private val onBookmarkClick: (Facility) -> Unit
) : RecyclerView.Adapter<FacilityListingAdapter.FacilityViewHolder>() {

    inner class FacilityViewHolder(val binding: ItemSavedFacilitiesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(facility: Facility) {
            binding.featuredImage.setImageResource(facility.imageResId)
            binding.facilityName.text = facility.name
            binding.locationTxt.text = facility.location
            binding.servicesTxt.text = facility.services
            binding.priceTxt.text = facility.price
            binding.ratingCard.visibility = View.VISIBLE


            val color = if (facility.isBookmarked) "#EA5B60" else "#FFFFFF"
            binding.bookmarkIcon.setColorFilter(color.toColorInt())

            binding.arrowIc.setOnClickListener {
                onItemClick(facility)
            }

            binding.bookmarkIcon.setOnClickListener {
                // Toggle bookmark state
                facility.isBookmarked = !facility.isBookmarked
                notifyItemChanged(adapterPosition) // update icon
                onBookmarkClick(facility) // notify outside
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacilityViewHolder {
        val binding = ItemSavedFacilitiesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FacilityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FacilityViewHolder, position: Int) {
        holder.bind(facilityList[position])
    }

    override fun getItemCount(): Int = facilityList.size
}