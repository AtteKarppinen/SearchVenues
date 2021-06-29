package com.example.searchvenues.view.list

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.searchvenues.R

/**
 * Holds similar values as the parameter view
 * @param view base view for Venue list items
 */
class VenueResultsViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {
    /**
     * Name
     */
    val name: TextView = view.findViewById(R.id.venue_name)
    /**
     * Formatted address, includes city and address if they are not null
     */
    val formattedAddress: TextView = view.findViewById(R.id.formatted_address)
    /**
     * Distance
     */
    val distance: TextView = view.findViewById(R.id.venue_distance)
}