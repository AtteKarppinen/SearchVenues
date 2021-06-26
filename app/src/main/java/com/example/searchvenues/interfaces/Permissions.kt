package com.example.searchvenues.interfaces

interface Permissions {

    fun getLocationPermission()

    fun hasLocationPermission() : Boolean
}