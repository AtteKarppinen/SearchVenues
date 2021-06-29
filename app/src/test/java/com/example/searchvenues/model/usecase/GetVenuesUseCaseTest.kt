package com.example.searchvenues.model.usecase

import com.example.searchvenues.interfaces.REST
import com.example.searchvenues.model.entities.Location
import com.example.searchvenues.model.entities.Venue
import com.example.searchvenues.model.template.Result
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for [GetVenuesUseCase]
 */
class GetVenuesUseCaseTest {

    private lateinit var useCase: GetVenuesUseCase
    private lateinit var rest: REST

    private val apiAccessPoint = "access point"
    private val apiVersion = "123"

    // For capturing url
    private val slot = slot<String>()

    @Before
    fun setup() {
        rest = mockk()

        useCase = GetVenuesUseCase(
            rest,
            apiAccessPoint,
            apiVersion
        )
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testExecute() = runBlocking {
        // Given
        val mockVenue = Venue("name", Location("address", "city", "123"))
        val mockLl = "12,21"
        val mockQuery = "query"
        val expectedUrl = "$apiAccessPoint&ll=$mockLl&v=$apiVersion&query=$mockQuery"
        every { rest.getVenues(capture(slot)) } returns listOf(mockVenue)

        // When
        val result = useCase(listOf(mockLl, mockQuery)) as Result.Success

        // Then
        verify(exactly = 1) {
            rest.getVenues(expectedUrl)
        }
        Assert.assertEquals(expectedUrl, slot.captured)
        Assert.assertTrue(result.data is List<Venue>)
    }
}