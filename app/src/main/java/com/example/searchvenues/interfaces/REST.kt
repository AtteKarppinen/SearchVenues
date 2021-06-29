package com.example.searchvenues.interfaces

import com.example.searchvenues.model.entities.Venue

/**
 * Interface for accessing 3rd party providers and managing online connections
 */
interface REST {
    /**
     * Fetch venues from provided url and return list
     */
    fun getVenues(fromUrl: String) : List<Venue>
}