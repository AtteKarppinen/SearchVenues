package com.example.searchvenues.view

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.searchvenues.R
import org.junit.*


/**
 * Espresso tests for [MainActivity]
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testCheckEditText() {
        // Given
        val query = "Sports"

        // When
        onView(withId(R.id.search_bar))
            .perform(typeText(query), closeSoftKeyboard())

        // Then
        onView(withId(R.id.search_bar))
            .check(matches(isDisplayed()))
            .check(matches(withText(query)))
    }
}