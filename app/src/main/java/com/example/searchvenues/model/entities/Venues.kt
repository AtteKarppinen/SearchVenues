package com.example.searchvenues.model.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

/**
 * Venue entity for matching REST results
 *
 * Note! This class should be improved, it violates clean architecture principle:
 * Os code inside model/entities
 */
@Parcelize
data class Venue(
    val name: String?,
    val location: @RawValue Location
) : Parcelable

/**
 * Location is represented in an array in received JSON. Map to needed values
 */
data class Location(
    val address: String?,
    val city: String?,
    val distance: String?
)