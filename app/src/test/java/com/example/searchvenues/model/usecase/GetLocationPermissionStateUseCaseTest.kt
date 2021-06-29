package com.example.searchvenues.model.usecase

import com.example.searchvenues.interfaces.Permissions
import com.example.searchvenues.model.template.Result
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for [GetLocationPermissionStateUseCase]
 */
class GetLocationPermissionStateUseCaseTest {

    private lateinit var stateUseCase: GetLocationPermissionStateUseCase
    private lateinit var permissions: Permissions

    @Before
    fun setup() {
        permissions = mockk()

        stateUseCase = GetLocationPermissionStateUseCase(permissions)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testExecuteReturnFalse() = runBlocking {
        // Given
        every { permissions.checkPermissionState(Permissions.Permission.LOCATION_PERMISSION) }returns
                Permissions.PermissionState.NOT_GRANTED

        // When
        val result = stateUseCase(Unit) as Result.Success

        // Then
        verify(exactly = 1) {
            permissions.checkPermissionState(Permissions.Permission.LOCATION_PERMISSION)
        }
        Assert.assertEquals(Permissions.PermissionState.NOT_GRANTED, result.data)
    }

    @Test
    fun testExecuteReturnTrue() = runBlocking {
        // Given
        every { permissions.checkPermissionState(Permissions.Permission.LOCATION_PERMISSION) }returns
                Permissions.PermissionState.GRANTED

        // When
        val result = stateUseCase(Unit) as Result.Success

        // Then
        verify(exactly = 1) {
            permissions.checkPermissionState(Permissions.Permission.LOCATION_PERMISSION)
        }
        Assert.assertEquals(Permissions.PermissionState.GRANTED, result.data)
    }
}