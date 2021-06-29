package com.example.searchvenues.model.usecase

import android.util.Log
import com.example.searchvenues.interfaces.REST
import com.example.searchvenues.model.entities.Venue
import com.example.searchvenues.model.template.CoroutineUseCase

/**
 * Use case for getting venues with given lat/long and query word
 *
 * @param apiAccessPoint is defined in gradle.properties and should include client id and secret
 * @param apiVersioningDate defined in same place, check versioning documentation here:
 * https://developer.foursquare.com/docs/places-api/versioning/
 */
class GetVenuesUseCase(
    private val rest: REST,
    private val apiAccessPoint: String,
    private val apiVersioningDate: String
) : CoroutineUseCase<List<String?>, List<Venue>?>() {

    companion object {
        private const val LOG_TAG = "GetVenuesUseCase"
    }

    override suspend fun execute(parameters: List<String?>): List<Venue> {
        Log.d(LOG_TAG, "execute with query: $parameters")
        val fourSquareUrl = "$apiAccessPoint&ll=${parameters[0]}&v=$apiVersioningDate&query=${parameters[1]}"
        return rest.getVenues(fourSquareUrl)
    }
}