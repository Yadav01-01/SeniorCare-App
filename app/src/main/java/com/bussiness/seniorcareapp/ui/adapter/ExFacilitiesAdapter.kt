package com.bussiness.seniorcareapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bussiness.seniorcareapp.R
import com.bussiness.seniorcareapp.data.model.PosterItem
import com.google.android.material.imageview.ShapeableImageView

class ExFacilitiesAdapter(
    private val items: List<PosterItem>
) : RecyclerView.Adapter<ExFacilitiesAdapter.PosterViewHolder>() {

    inner class PosterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val posterImage: ShapeableImageView = itemView.findViewById(R.id.posterImage)
        val titleText: TextView = itemView.findViewById(R.id.titleText)
        val descriptionText: TextView = itemView.findViewById(R.id.descriptionText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ex_facilities, parent, false)
        return PosterViewHolder(view)
    }

    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        val item = items[position]
        holder.posterImage.setImageResource(item.imageResId)
        holder.titleText.text = item.title
        holder.descriptionText.text = item.description
    }

    override fun getItemCount(): Int = items.size
}
