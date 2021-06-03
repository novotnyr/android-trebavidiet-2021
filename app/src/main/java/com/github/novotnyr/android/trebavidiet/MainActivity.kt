package com.github.novotnyr.android.trebavidiet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var placeRecyclerView: RecyclerView

    private lateinit var placeListAdapter: PlaceListAdapter

    private lateinit var selectionTracker: SelectionTracker<Long>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        placeRecyclerView = findViewById(R.id.placeRecyclerView)

        placeListAdapter = PlaceListAdapter().apply {
            placeRecyclerView.adapter = this
            submitList(getInitialPlaces())
        }

        selectionTracker = SelectionTracker.Builder("places-selection",
                placeRecyclerView,
                StableIdKeyProvider(placeRecyclerView),
                PlaceDetailsLookup(placeRecyclerView),
                StorageStrategy.createLongStorage()
        ).build()

        placeListAdapter.selectionTracker = selectionTracker

        PlaceSelectionObserver(this@MainActivity, selectionTracker)
                .apply {
                    onDeleteItemsListener = placeListAdapter::removePlaces
                    selectionTracker.addObserver(this)
                }
    }

    private fun getInitialPlaces() = mutableListOf(
            Place(1L, "Cathedral of St Elizabeth"),
            Place(2L, "Collosseum"),
            Place(3L, "St. Michael's Chapel"),
            Place(4L, "Botanical Garden"),
            Place(5L, "ZOO"))
}