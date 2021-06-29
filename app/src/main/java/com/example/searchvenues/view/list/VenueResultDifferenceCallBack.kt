package com.example.searchvenues.view.list

import androidx.recyclerview.widget.DiffUtil
import com.example.searchvenues.model.entities.Venue

/**
 * adapter.notifyDataSetChanged is not required when using DiffUtil callbacks
 */
class VenueResultDifferenceCallBack : DiffUtil.ItemCallback<Venue>() {
    override fun areItemsTheSame(oldItem: Venue, newItem: Venue): Boolean {
        return oldItem.name == newItem.name &&
                oldItem.location == newItem.location
    }

    override fun areContentsTheSame(oldItem: Venue, newItem: Venue): Boolean {
        return oldItem.name == newItem.name &&
                oldItem.location == newItem.location
    }
}