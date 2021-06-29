package com.example.searchvenues.presenter

import com.example.searchvenues.interfaces.Contract
import com.example.searchvenues.interfaces.Permissions
import com.example.searchvenues.model.MainInteractor
import com.example.searchvenues.model.template.Result
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for [MainActivityPresenter]
 */
class MainActivityPresenterTest {


    private lateinit var mainActivityPresenter: MainActivityPresenter
    private lateinit var mainInteractor: MainInteractor

    private lateinit var mockView: Contract.View

    @Before
    fun setup() {
        mainInteractor = mockk(relaxed = true)
        mockView = mockk(relaxed = true)

        mainActivityPresenter = MainActivityPresenter(mainInteractor)

        mainActivityPresenter.attachView(mockView)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testCheckOnLaunchPermissionNotGranted() {
        // Given
        coEvery { mainInteractor.getLocationPermissionState() } returns
                Result.Success(Permissions.PermissionState.NOT_GRANTED)

        // When
        mainActivityPresenter.checkOnLaunch()

        // Then
        coVerify(exactly = 1) {
            mainInteractor.getLocationPermissionState()
            mainInteractor.updateLatLongData()
        }
        verify(exactly = 1) {
            mockView.lockUI()
            mockView.requestLocationPermission()
        }
    }

    @Test
    fun testCheckOnLaunchPermissionGranted() {
        // Given
        coEvery { mainInteractor.getLocationPermissionState() } returns
                Result.Success(Permissions.PermissionState.GRANTED)

        // When
        mainActivityPresenter.checkOnLaunch()

        // Then
        coVerify(exactly = 1) {
            mainInteractor.getLocationPermissionState()
            mainInteractor.updateLatLongData()
        }
        verify(exactly = 1) {
            mockView.unlockUI()
        }
    }

    @Test
    fun testOnSearchChangeNullQuery() {
        // Given
        val queryMock = null

        // When
        mainActivityPresenter.onSearchChange(queryMock)

        verify(exactly = 1) {
            mockView.emptySearchResults()
        }
    }

    @Test
    fun testOnSearchChangeEmptyQuery() {
        // Given
        val queryMock = ""

        // When
        mainActivityPresenter.onSearchChange(queryMock)

        verify(exactly = 1) {
            mockView.emptySearchResults()
        }
    }
}