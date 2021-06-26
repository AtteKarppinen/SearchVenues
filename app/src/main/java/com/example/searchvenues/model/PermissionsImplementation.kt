package com.example.searchvenues.model

import android.util.Log
import com.example.searchvenues.interfaces.Permissions

class PermissionsImplementation : Permissions {

    companion object {
        private const val LOG_TAG = "PermissionsImpl"
    }

    override fun getLocationPermission() {
        Log.d(LOG_TAG, "getLocationPermission")
    }

    override fun hasLocationPermission() : Boolean {
        Log.d(LOG_TAG, "hasLocationPermission")
        return true
    }
}