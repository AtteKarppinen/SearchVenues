package com.example.searchvenues.model.usecase

import android.util.Log
import com.example.searchvenues.interfaces.Location
import com.example.searchvenues.model.template.CoroutineUseCase

/**
 * Use case for getting latitude/longitude in a string
 */
class GetLatLongDataUseCase(
    private val location: Location
) : CoroutineUseCase<Unit, String>() {

    companion object {
        private const val LOG_TAG = "GetLatLongDataUseCase"
    }

    override suspend fun execute(parameters: Unit): String {
        Log.d(LOG_TAG, "execute")
        return location.getLatLongData()
    }
}