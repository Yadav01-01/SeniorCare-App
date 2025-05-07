package com.bussiness.seniorcareapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bussiness.seniorcareapp.data.model.OnboardingItem
import com.bussiness.seniorcareapp.databinding.OnboardingItemBinding

class OnboardingAdapter(private val items: List<OnboardingItem>, private val onSkipClick: () -> Unit,private val onNextClick: (position: Int) -> Unit ) :
    RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    inner class OnboardingViewHolder(private val binding: OnboardingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: OnboardingItem) {
            binding.topImage.setImageResource(item.imageResId)
            binding.title.text = item.title
            binding.tvDescription.text = item.description
            binding.skipButton.setOnClickListener { onSkipClick() }
            binding.curveImgBtn.setOnClickListener { onNextClick(position) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val binding = OnboardingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OnboardingViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
