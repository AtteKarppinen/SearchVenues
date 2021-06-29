package com.example.searchvenues.model.usecase

import android.util.Log
import com.example.searchvenues.interfaces.Permissions
import com.example.searchvenues.model.template.CoroutineUseCase

/**
 * Use case for getting location permission state
 */
class GetLocationPermissionStateUseCase(
    private val permissions: Permissions
) : CoroutineUseCase<Unit, Permissions.PermissionState>() {

    companion object {
        private const val LOG_TAG = "GetLocPermissionState"
    }

    override suspend fun execute(parameters: Unit) : Permissions.PermissionState {
        Log.d(LOG_TAG, "execute")
        return permissions.checkPermissionState(Permissions.Permission.LOCATION_PERMISSION)
    }
}