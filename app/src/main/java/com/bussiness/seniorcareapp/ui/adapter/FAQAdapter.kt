package com.bussiness.seniorcareapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bussiness.seniorcareapp.R
import com.bussiness.seniorcareapp.data.model.FAQItem
import com.bussiness.seniorcareapp.databinding.ItemFaqBinding

class FAQAdapter(private val faqList: List<FAQItem>) : RecyclerView.Adapter<FAQAdapter.FAQViewHolder>() {

    inner class FAQViewHolder(private val binding: ItemFaqBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(faqItem: FAQItem, position: Int) {
            binding.tvQuestion.text = faqItem.question
            binding.tvAnswer.text = faqItem.answer

            // Set visibility based on expanded state
            binding.tvAnswer.visibility = if (faqItem.isExpanded) View.VISIBLE else View.GONE
            binding.imgExpand.setImageResource(
                if (faqItem.isExpanded) R.drawable.arrow_down else R.drawable.arrow_right
            )

            // Expand/Collapse when clicking the icon
            binding.root.setOnClickListener {
                faqItem.isExpanded = !faqItem.isExpanded
                notifyItemChanged(position) // Update the specific item
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FAQViewHolder {
        val binding = ItemFaqBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FAQViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FAQViewHolder, position: Int) {
        holder.bind(faqList[position], position)
    }

    override fun getItemCount(): Int = faqList.size
}
