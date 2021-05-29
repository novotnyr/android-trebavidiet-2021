package com.github.novotnyr.android.trebavidiet

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


private fun ViewGroup.inflateChild() =
    LayoutInflater
        .from(context)
        .inflate(
            android.R.layout.simple_list_item_activated_1,
            this,
            false
        )

class PlaceViewHolder(recyclerView: ViewGroup) : RecyclerView.ViewHolder(
    recyclerView.inflateChild()
) {
    fun bind(place: Place, isSelected: Boolean) {
        val textView = itemView as TextView
        textView.text = place.description
        textView.isActivated = isSelected
    }
}

object PlaceDiff : DiffUtil.ItemCallback<Place>() {
    override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem.description == newItem.description
    }
}