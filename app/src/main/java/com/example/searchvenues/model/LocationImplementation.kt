package com.example.searchvenues.model

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.searchvenues.interfaces.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

/**
 * Implementation class for the [Location] interface
 */
class LocationImplementation(
    private val context: Context
) : Location {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    /**
     * The return value. Use backup as initialization value
     */
    private var latLongData = MainInteractor.HELSINKI_LL_BACKUP

    override suspend fun getLatLongData():String {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        getLastKnownLocation()
        return latLongData
    }

    /**
     * Permissions are being handled elsewhere. Safe to ignore
     */
    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                latLongData = "${it.latitude},${it.longitude}"
            }
        }
    }
}