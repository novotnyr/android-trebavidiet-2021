package com.github.novotnyr.android.trebavidiet

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemDetailsLookup.ItemDetails
import androidx.recyclerview.widget.RecyclerView

class SimpleItemDetails(private val viewHolder: RecyclerView.ViewHolder) : ItemDetails<Long>() {
    override fun getPosition(): Int = viewHolder.adapterPosition

    override fun getSelectionKey(): Long = viewHolder.itemId
}

class PlaceDetailsLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {
    override fun getItemDetails(motionEvent: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(motionEvent.x, motionEvent.y) ?: return null
        val viewHolder = recyclerView.getChildViewHolder(view)
        return SimpleItemDetails(viewHolder)
    }
}
