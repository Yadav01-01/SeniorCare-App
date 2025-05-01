package com.bussiness.seniorcareapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bussiness.seniorcareapp.data.model.FtProfileItem
import com.bussiness.seniorcareapp.databinding.ItemFeatureProviderBinding

class FtProviderAdapter(
    private val profileList: List<FtProfileItem>,
    private val onViewProfileClick: (FtProfileItem) -> Unit,
    private val onCallClick: (FtProfileItem) -> Unit
) : RecyclerView.Adapter<FtProviderAdapter.ProfileViewHolder>() {

    inner class ProfileViewHolder(val binding: ItemFeatureProviderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val binding = ItemFeatureProviderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val profile = profileList[position]
        with(holder.binding) {
            image.setImageResource(profile.imageRes)
            name.text = profile.name
            descriptionText.text = profile.description
            locationTxt.text = profile.location
            servicesTxt.text = profile.services
            websiteTxt.text = profile.website

            viewProfileBtn.setOnClickListener {
                onViewProfileClick(profile)
            }

            callIcon.setOnClickListener {
                onCallClick(profile)
            }
        }
    }

    override fun getItemCount(): Int = profileList.size
}
