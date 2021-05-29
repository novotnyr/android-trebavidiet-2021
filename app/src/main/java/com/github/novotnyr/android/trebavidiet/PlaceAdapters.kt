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


class PlaceListAdapter : ListAdapter<Place, PlaceViewHolder>(PlaceDiff) {
    init {
        setHasStableIds(true) //<2>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        return PlaceViewHolder(parent) //<3>
    }

    override fun onBindViewHolder(viewHolder: PlaceViewHolder, position: Int) {
        val place = getItem(position)
        viewHolder.bind(place, false) //<4>
    }

    override fun getItemId(position: Int): Long { //<5>
        val place = getItem(position)
        return place.id
    }
}