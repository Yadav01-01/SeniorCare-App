package com.bussiness.seniorcareapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.bussiness.seniorcareapp.data.model.Facility
import com.bussiness.seniorcareapp.databinding.ItemSavedFacilitiesBinding
import com.bussiness.seniorcareapp.utils.SessionManager

class FeaturedFacilityAdapter(
    private val facilityList: List<Facility>,
    private val sessionManager: SessionManager,
    private val onItemClick: (Facility) -> Unit,
    private val onBookmarkClick: (Facility) -> Unit
) : RecyclerView.Adapter<FeaturedFacilityAdapter.FacilityViewHolder>() {

    inner class FacilityViewHolder(val binding: ItemSavedFacilitiesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(facility: Facility) {
            binding.featuredImage.setImageResource(facility.imageResId)
            binding.facilityName.text = facility.name
            binding.locationTxt.text = facility.location
            binding.servicesTxt.text = facility.services
            binding.priceTxt.text = facility.price

            if (sessionManager.isLoggedIn()) {
                binding.ratingCard.visibility = View.GONE
            } else {
                binding.ratingCard.visibility = View.VISIBLE
            }
            // Set initial bookmark icon color
            val color = if (facility.isBookmarked) "#EA5B60" else "#FFFFFF"
            binding.bookmarkIcon.setColorFilter(color.toColorInt())

            binding.root.setOnClickListener {
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
