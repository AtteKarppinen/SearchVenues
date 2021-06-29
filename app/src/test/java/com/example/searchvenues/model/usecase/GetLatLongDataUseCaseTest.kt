package com.example.searchvenues.model.usecase

import com.example.searchvenues.interfaces.Location
import com.example.searchvenues.model.template.Result
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for [GetLatLongDataUseCase]
 */
class GetLatLongDataUseCaseTest {

    private lateinit var useCase: GetLatLongDataUseCase
    private lateinit var location: Location

    private var expectedResult = "12,21"

    @Before
    fun setup() {
        location = mockk()

        useCase = GetLatLongDataUseCase(location)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testExecuteReturnCorrectList() = runBlocking {
        // Given
        val mockLl = "12,21"
        coEvery { location.getLatLongData() } returns mockLl

        // When
        val result = useCase(Unit) as Result.Success

        // Then
        coVerify(exactly = 1) {
            location.getLatLongData()
        }
        Assert.assertEquals(expectedResult, result.data)
    }

    @Test
    fun testExecuteReturnIncorrectList() = runBlocking {
        // Given
        val mockLl = "-999,-999"
        coEvery { location.getLatLongData() } returns mockLl

        // When
        val result = useCase(Unit) as Result.Success

        // Then
        coVerify(exactly = 1) {
            location.getLatLongData()
        }
        Assert.assertNotEquals(expectedResult, result.data)
    }
}