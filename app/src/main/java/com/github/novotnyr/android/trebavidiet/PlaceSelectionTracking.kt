package com.github.novotnyr.android.trebavidiet

import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
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
) : SelectionTracker.SelectionObserver<Long>(),
        ActionMode.Callback {

    private var actionMode: ActionMode? = null

    var onDeleteItemsListener: (List<Long>) -> Unit = {}

    override fun onSelectionChanged() {
        if (selectionTracker.hasSelection()) {
            if (actionMode == null) {
                actionMode = activity.startSupportActionMode(this)
            }
            actionMode?.title = "Selected: " + selectionTracker.selection.size()
        } else {
            actionMode?.finish()
        }
    }

    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
        mode.menuInflater.inflate(R.menu.place_selection_cab, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        return if (item.itemId == R.id.placeDeleteMenuItem) {
            onDeleteItemsListener(selectionTracker.selection.toList())
            mode.finish()
            true
        } else {
            false
        }
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        selectionTracker.clearSelection()
        actionMode = null
    }
}
