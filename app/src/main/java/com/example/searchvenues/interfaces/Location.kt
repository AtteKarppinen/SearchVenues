package com.example.searchvenues.interfaces

/**
 * Location interface for requesting location data from the system
 */
interface Location {
    /**
     * Return latitude and longitude as a string
     */
    suspend fun getLatLongData(): String
}