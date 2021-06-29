package com.example.searchvenues.view.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.searchvenues.R
import com.example.searchvenues.model.entities.Venue

/**
 * Adapter for handling search results
 */
class VenueResultsAdapter :
    ListAdapter<Venue, VenueResultsViewHolder>(VenueResultDifferenceCallBack()) {

    companion object {
        private const val LOG_TAG = "VenueResultsAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueResultsViewHolder {
        Log.d(LOG_TAG, "onCreateViewHolder")
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item, parent, false)
        return VenueResultsViewHolder(view)
    }

    override fun onBindViewHolder(holder: VenueResultsViewHolder, position: Int) {
        Log.d(LOG_TAG, "onBindViewHolder")
        getItem(position).let { venue ->
            Log.d(LOG_TAG, "$venue")

            val formattedAddress = listOfNotNull(venue.location.city, venue.location.address)
                .joinToString { it }
            if (formattedAddress.isEmpty()) {
                holder.formattedAddress.visibility = View.GONE
            }

            holder.name.text = venue.name
            holder.formattedAddress.text = holder.itemView.context
                .getString(R.string.list_item_address, formattedAddress)
            holder.distance.text = holder.itemView.context
                .getString(R.string.list_item_distance, venue.location.distance)
        }
    }
}