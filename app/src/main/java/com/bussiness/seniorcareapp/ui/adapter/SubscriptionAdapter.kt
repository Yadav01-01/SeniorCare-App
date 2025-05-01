package com.bussiness.seniorcareapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bussiness.seniorcareapp.data.model.SubscriptionPlan
import com.bussiness.seniorcareapp.databinding.ItemSubscriptionBinding

class SubscriptionAdapter(
    private val plans: List<SubscriptionPlan>
) : RecyclerView.Adapter<SubscriptionAdapter.PlanViewHolder>() {

    inner class PlanViewHolder(val binding: ItemSubscriptionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
        val binding = ItemSubscriptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        val plan = plans[position]
        with(holder.binding) {
            subscriptionPlan.text = plan.name
            planAmount.text = plan.price
            // Set features (you can customize this better with a RecyclerView or dynamic views)
            feature1.text = plan.features.getOrNull(0) ?: ""
            feature2.text = plan.features.getOrNull(1) ?: ""
            feature3.text = plan.features.getOrNull(2) ?: ""
            feature4.text = plan.features.getOrNull(3) ?: ""
            feature5.text = plan.features.getOrNull(4) ?: ""
        }
    }

    override fun getItemCount(): Int = plans.size
}
