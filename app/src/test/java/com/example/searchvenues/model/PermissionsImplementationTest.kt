package com.example.searchvenues.model

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.example.searchvenues.interfaces.Permissions
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for [PermissionsImplementation]
 */
class PermissionsImplementationTest {

    private lateinit var permissions: PermissionsImplementation
    private lateinit var context: Context

    @Before
    fun setUp() {
        mockkStatic(ContextCompat::class)
        mockkStatic(PackageManager::class)
        context = mockk(relaxed = true)

        permissions = PermissionsImplementation(context)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testCheckLocationPermissionState() {
        // Given
        every { ContextCompat.checkSelfPermission(any(), any()) } returns 1

        // When
        permissions.checkPermissionState(Permissions.Permission.LOCATION_PERMISSION)

        // Then
        verify(exactly = 1) {
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
}