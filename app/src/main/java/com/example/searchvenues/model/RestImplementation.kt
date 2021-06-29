package com.example.searchvenues.model

import android.util.Log
import com.example.searchvenues.interfaces.REST
import com.example.searchvenues.model.entities.Venue
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import org.json.JSONObject
import java.net.MalformedURLException
import java.net.URL

/**
 * Implementation class for the [REST] interface
 */
class RestImplementation : REST {

    companion object {
        private const val LOG_TAG = "RestImplementation"
    }

    override fun getVenues(fromUrl: String): List<Venue> {
        var venues: List<Venue> = emptyList()

        try {
            val json = URL(fromUrl).readText()
            val venuesJsonArray = JSONObject(json).getJSONObject("response").getJSONArray("venues")
            val testType = object : TypeToken<List<Venue>>() {}.type
            venues = Gson().fromJson(venuesJsonArray.toString(), testType)
            Log.d(LOG_TAG, "Returning ${venues.size} venues")
        } catch (e: MalformedURLException) {
            assert(false) { "${e.message}"}
            Log.e(LOG_TAG, "Error: ${e.message}")
        } catch (e: JSONException) {
            assert(false) { "${e.message}"}
            Log.e(LOG_TAG, "Error: ${e.message}")
        }

        return venues
    }
}