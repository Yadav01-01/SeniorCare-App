package com.bussiness.seniorcareapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bussiness.seniorcareapp.databinding.ItemDeleteFeedbackBinding

class ReasonAdapter(
    private val reasonList: List<String>,
    private val onItemClick: (String) -> Unit,
    private val onOtherItemClick: (String) -> Unit
) : RecyclerView.Adapter<ReasonAdapter.ReasonViewHolder>() {

    inner class ReasonViewHolder(private val binding: ItemDeleteFeedbackBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(reason: String) {
            binding.reasonTxt.text = reason

            binding.root.setOnClickListener {
                if (reason.equals("other", ignoreCase = true)) {
                    onOtherItemClick(reason)
                } else {
                    onItemClick(reason)
                }
            }
            if (reason.equals("other", ignoreCase = true)){
                binding.bottomLine.visibility = View.GONE
            }else{
                binding.bottomLine.visibility = View.VISIBLE
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
