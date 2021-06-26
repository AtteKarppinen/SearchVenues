package com.example.searchvenues.model

import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class PermissionsImplementationTest {

    private lateinit var permissions: PermissionsImplementation

    @Before
    fun setUp() {
        mockkStatic(ContextCompat::class)
        mockkStatic(PackageManager::class)
        mockkStatic(Activity::class)
        mockkStatic(Log::class)

        permissions = PermissionsImplementation()
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testGetLocationPermission() {
        // Given
        // When
        permissions.getLocationPermission()

        // Then
        verify(exactly = 1) {
            Activity().requestPermissions(any(), any())
        }
    }

    @Test
    fun testHasLocationPermissionReturnTrue() {
        // Given
        every { ContextCompat.checkSelfPermission(any(), any()) } returns
                PackageManager.PERMISSION_GRANTED

        // When
        val result = permissions.hasLocationPermission()

        // Then
        verify(exactly = 1) {
            ContextCompat.checkSelfPermission(any(), any())
        }
        assertTrue(result)
    }

    @Test
    fun testHasLocationPermissionReturnFalse() {
        // Given
        every { ContextCompat.checkSelfPermission(any(), any()) } returns
                PackageManager.PERMISSION_DENIED

        // When
        val result = permissions.hasLocationPermission()

        // Then
        verify(exactly = 1) {
            ContextCompat.checkSelfPermission(any(), any())
        }
        assertFalse(result)
    }
}