package com.bussiness.seniorcareapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bussiness.seniorcareapp.data.model.Amenity
import com.bussiness.seniorcareapp.databinding.ItemFilterAmenitiesBinding

class AmenitiesAdapter(
    private val amenities: List<Amenity>,
    private val onCheckedChanged: (Amenity, Boolean) -> Unit
) : RecyclerView.Adapter<AmenitiesAdapter.AmenityViewHolder>() {

    inner class AmenityViewHolder(val binding: ItemFilterAmenitiesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(amenity: Amenity) {
            binding.checkbox.text = amenity.name
            binding.checkbox.isChecked = amenity.isSelected

            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                amenity.isSelected = isChecked
                onCheckedChanged(amenity, isChecked)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmenityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFilterAmenitiesBinding.inflate(inflater, parent, false)
        return AmenityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AmenityViewHolder, position: Int) {
        holder.bind(amenities[position])
    }

    override fun getItemCount(): Int = amenities.size
}
