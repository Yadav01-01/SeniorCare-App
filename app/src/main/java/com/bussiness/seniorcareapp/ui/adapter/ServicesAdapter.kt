package com.bussiness.seniorcareapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bussiness.seniorcareapp.data.model.ServiceItem
import com.bussiness.seniorcareapp.databinding.ItemServiceBinding

class ServicesAdapter(private val serviceList: List<ServiceItem>) :
    RecyclerView.Adapter<ServicesAdapter.ServiceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val binding = ItemServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val serviceItem = serviceList[position]
        holder.bind(serviceItem)
    }

    override fun getItemCount(): Int = serviceList.size

    inner class ServiceViewHolder(private val binding: ItemServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(serviceItem: ServiceItem) {
            binding.apply {
                heading.text = serviceItem.heading
                descriptionText.text = serviceItem.description
                descriptionText1.text = serviceItem.subDescription1
                descriptionText2.text = serviceItem.subDescription2
                descriptionText3.text = serviceItem.subDescription3
                descriptionText4.text = serviceItem.subDescription4
                quest.text = serviceItem.quest
                serviceImage.setImageResource(serviceItem.imageResId)
            }
        }
    }
}
