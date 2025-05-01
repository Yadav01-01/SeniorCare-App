package com.bussiness.seniorcareapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bussiness.seniorcareapp.data.model.Facility
import com.bussiness.seniorcareapp.databinding.ItemSavedFacilitiesBinding

class FacilityAdapter(
    private val facilityList: List<Facility>,
    private val onItemClick: (Facility) -> Unit
) : RecyclerView.Adapter<FacilityAdapter.FacilityViewHolder>() {

    inner class FacilityViewHolder(val binding: ItemSavedFacilitiesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(facility: Facility) {
            binding.featuredImage.setImageResource(facility.imageResId)
            binding.facilityName.text = facility.name
            binding.locationTxt.text = facility.location
            binding.servicesTxt.text = facility.services
            binding.priceTxt.text = facility.price

            binding.arrowIc.setOnClickListener {
                onItemClick(facility)
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