package com.example.searchvenues.interfaces

/**
 * Permission interface so core code is not dependent on any system values
 */
interface Permissions {
    /**
     * Get state for given permission
     */
    fun checkPermissionState(permission: Permission) : PermissionState

    enum class PermissionState {
        GRANTED,
        NOT_GRANTED
    }

    /**
     * Required permissions for the app. Should be mapped with corresponding system permissions
     */
    enum class Permission {
        LOCATION_PERMISSION
    }
}