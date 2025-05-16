package com.bussiness.seniorcareapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bussiness.seniorcareapp.R
import com.bussiness.seniorcareapp.databinding.ItemDeleteFeedbackBinding

class ReasonAdapter(
    private val reasonList: List<String>,
    private val onItemClick: (String) -> Unit,
    private val onOtherItemClick: (Boolean) -> Unit, // pass expanded state
    private var isOtherExpanded: Boolean = false
) : RecyclerView.Adapter<ReasonAdapter.ReasonViewHolder>() {

    inner class ReasonViewHolder(private val binding: ItemDeleteFeedbackBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(reason: String) {
            binding.reasonTxt.text = reason

            if (reason.equals("other", ignoreCase = true)) {
                binding.arrowIc.setImageResource(
                    if (isOtherExpanded) R.drawable.arrow_down else R.drawable.arrow_right
                )
            }

            binding.root.setOnClickListener {
                if (reason.equals("other", ignoreCase = true)) {
                    isOtherExpanded = !isOtherExpanded
                    notifyItemChanged(adapterPosition)
                    onOtherItemClick(isOtherExpanded) // Notify fragment of new state
                } else {
                    onItemClick(reason)
                }
            }

            binding.bottomLine.visibility =
                if (reason.equals("other", ignoreCase = true)) View.GONE else View.VISIBLE
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
