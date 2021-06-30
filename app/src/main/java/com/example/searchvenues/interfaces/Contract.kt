package com.example.searchvenues.interfaces

import com.example.searchvenues.model.entities.Venue
import com.example.searchvenues.model.template.Result

/**
 * Main contract for model-presenter and view-presenter. Interfaces could be separated into own
 * files, but until the amount of functions is manageable, it might be simpler to keep them together
 */
interface Contract {

    interface View {
        /**
         * Progress bar during load
         */
        fun showProgress()

        /**
         * Hide progress bar
         */
        fun hideProgress()

        /**
         * Loads received venues into a list. Null will empty the list
         */
        fun loadListView(venues: List<Venue>?)

        /**
         * Empty list when search bar is empty
         */
        fun emptySearchResults()

        /**
         * Permission request has to be done on UI
         */
        fun requestLocationPermission()

        /**
         * Lock UI if missing permissions
         */
        fun lockUI()

        /**
         * Unlock UI
         */
        fun unlockUI()
    }

    interface Interactor {
        /**
         * Get venues and return them in a list
         */
        suspend fun getVenues(query: String?) : Result<List<Venue>?>

        /**
         * Get Location permission state
         */
        suspend fun getLocationPermissionState() : Result<Permissions.PermissionState>

        /**
         * Update last know lat/long data on app launch
         */
        suspend fun updateLatLongData() : Result<String>
    }

    interface Presenter {
        /**
         * Called every time user changes search word
         */
        fun onSearchChange(query: String?)

        /**
         * Checks done on app launch. Includes checking location permission state and
         * updating last known lat/long data
         */
        fun checkOnLaunch()

        /**
         * Attach contract's view interface to presenter
         */
        fun attachView(view: View)

        /**
         * Update last known location
         */
        fun updateLastKnownLocation()

        /**
         * Cleaning
         */
        fun onDestroy()
    }
}