package com.example.searchvenues.model

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.searchvenues.interfaces.Permissions

/**
 * Implementation for the [Permissions] interface
 */
class PermissionsImplementation(
    private val context: Context
) : Permissions {

    companion object {
        private const val LOG_TAG = "PermissionsImpl"
    }

    override fun checkPermissionState(permission: Permissions.Permission): Permissions.PermissionState {
        Log.d(LOG_TAG, "checkPermissionState for $permission")
        return when (ContextCompat.checkSelfPermission(context, mapPermissions(permission))) {
            PackageManager.PERMISSION_GRANTED -> Permissions.PermissionState.GRANTED
            PackageManager.PERMISSION_DENIED -> Permissions.PermissionState.NOT_GRANTED
            else -> Permissions.PermissionState.NOT_GRANTED
        }
    }

    /**
     * Mapper to match our permissions with system's permissions
     */
    private fun mapPermissions(permission: Permissions.Permission) : String {
        return when (permission) {
            Permissions.Permission.LOCATION_PERMISSION -> Manifest.permission.ACCESS_FINE_LOCATION
        }
    }
}