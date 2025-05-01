package com.bussiness.seniorcareapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bussiness.seniorcareapp.databinding.ItemDeleteFeedbackBinding

class ReasonAdapter(
    private val reasonList: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<ReasonAdapter.ReasonViewHolder>() {

    inner class ReasonViewHolder(private val binding: ItemDeleteFeedbackBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(reason: String) {
            binding.reasonTxt.text = reason
            binding.root.setOnClickListener {
                onItemClick(reason)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReasonViewHolder {
        val binding = ItemDeleteFeedbackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReasonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReasonViewHolder, position: Int) {
        holder.bind(reasonList[position])
    }

    override fun getItemCount(): Int = reasonList.size
}
