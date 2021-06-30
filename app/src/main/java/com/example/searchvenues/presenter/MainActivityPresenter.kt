package com.example.searchvenues.presenter

import android.util.Log
import com.example.searchvenues.interfaces.Contract
import com.example.searchvenues.interfaces.Permissions
import com.example.searchvenues.model.entities.Venue
import com.example.searchvenues.model.template.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Presenter for MainActivity and MainInteractor. Implements [Contract.Presenter]
 */
class MainActivityPresenter(
    private val mainInteractor: Contract.Interactor
) : Contract.Presenter {

    companion object {
        private const val LOG_TAG = "MainActivityPresenter"

        /**
         * Use small delay for hiding the progress bar for better experience.
         * This will not block the UI
         */
        private const val SMOOTH_TRANSITION_DELAY = 500L
    }

    /**
     * MainActivity is tied to this variable
     */
    private var view: Contract.View? = null
    /**
     * Variable for holding current venues
     */
    private var venueList: List<Venue>? = listOf()

    override fun checkOnLaunch() {
        GlobalScope.launch {
            when (val result = mainInteractor.getLocationPermissionState()) {
                is Result.Success -> {
                    Log.d(LOG_TAG, "permission state: ${result.data}")
                    if (result.data == Permissions.PermissionState.NOT_GRANTED) {
                        view?.lockUI()
                        view?.requestLocationPermission()
                    } else {
                        view?.unlockUI()
                    }
                }
                is Result.Error -> {
                    assert(false) { "Error message: ${result.exception.message}" }
                    Log.e(LOG_TAG, "Error message: ${result.exception.message}")
                }
            }
            updateLastKnownLocation()
        }
    }

    override fun updateLastKnownLocation() {
        GlobalScope.launch {
            mainInteractor.updateLatLongData()
        }
    }

    override fun attachView(view: Contract.View) {
        this.view = view
    }

    override fun onSearchChange(query: String?) {
        Log.d(LOG_TAG, "onSearchChange: $query")
        when (query) {
            null, "" -> view?.emptySearchResults()
            else -> updateAndLoadVenueList(query)
        }
    }

    /**
     * Fetch venue list using coroutines and update on UI thread
     */
    private fun updateAndLoadVenueList(query: String) {
        view?.showProgress()
        GlobalScope.launch {
            venueList = when (val result = mainInteractor.getVenues(query)) {
                is Result.Success -> result.data?.map { it }
                is Result.Error -> {
                    assert(false) { "Error message: ${result.exception}" }
                    Log.e(LOG_TAG, "Error message: ${result.exception}")
                    null
                }
                else -> null
            }
        }.invokeOnCompletion {
            GlobalScope.launch(Dispatchers.Main) {
                view?.loadListView(venueList)
                delay(SMOOTH_TRANSITION_DELAY)
                view?.hideProgress()
            }
        }
    }

    override fun onDestroy() {
        Log.d(LOG_TAG, "onDestroy")
        view = null
    }
}
