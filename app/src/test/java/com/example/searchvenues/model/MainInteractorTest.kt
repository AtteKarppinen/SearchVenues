package com.example.searchvenues.model

import com.example.searchvenues.interfaces.Permissions
import com.example.searchvenues.model.template.Result
import com.example.searchvenues.model.usecase.GetLatLongDataUseCase
import com.example.searchvenues.model.usecase.GetLocationPermissionStateUseCase
import com.example.searchvenues.model.usecase.GetVenuesUseCase
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for [MainInteractor]
 */
class MainInteractorTest {

    private lateinit var mainInteractor: MainInteractor
    private lateinit var getVenuesUseCase: GetVenuesUseCase
    private lateinit var getLocationPermissionStateUseCase: GetLocationPermissionStateUseCase
    private lateinit var getLatLongDataUseCase: GetLatLongDataUseCase

    @Before
    fun setup() {
        getVenuesUseCase = mockk()
        getLocationPermissionStateUseCase = mockk()
        getLatLongDataUseCase = mockk()

        mainInteractor = MainInteractor(
            getVenuesUseCase,
            getLocationPermissionStateUseCase,
            getLatLongDataUseCase
        )
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testGetVenues() = runBlocking {
        // Given
        coEvery { getVenuesUseCase(any()) } returns Result.Success(listOf())
        coEvery { getLatLongDataUseCase(Unit) } returns Result.Success("12,21")

        // When
        mainInteractor.getVenues("query")

        // Then
        coVerify(exactly = 1) {
            getVenuesUseCase(any())
        }
    }

    @Test
    fun testGetLocationPermissionState() = runBlocking {
        // Given
        coEvery { getLocationPermissionStateUseCase(Unit) } returns Result.Success(Permissions.PermissionState.GRANTED)
        // When
        mainInteractor.getLocationPermissionState()

        // Then
        coVerify(exactly = 1) {
            getLocationPermissionStateUseCase(Unit)
        }
    }
}