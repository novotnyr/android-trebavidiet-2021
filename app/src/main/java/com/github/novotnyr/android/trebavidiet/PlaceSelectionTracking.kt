package com.github.novotnyr.android.trebavidiet

import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemDetailsLookup.ItemDetails
import androidx.recyclerview.selection.SelectionTracker
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

class PlaceSelectionObserver(private val activity: AppCompatActivity,
                            private val selectionTracker: SelectionTracker<Long>
) : SelectionTracker.SelectionObserver<Long>() {
    override fun onSelectionChanged() {
        if (selectionTracker.hasSelection()) {
            activity.title = "Selected: " + selectionTracker.selection.size()
        } else {
            activity.setTitle(R.string.app_name)
        }
    }
}
