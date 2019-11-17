package me.vaughandroid.intervaltimer.configuration

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import me.vaughandroid.intervaltimer.BlankActivity
import me.vaughandroid.intervaltimer.R
import me.vaughandroid.intervaltimer.time.seconds
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ConfigurationFragmentTests {

    @get:Rule
    val activityTestRule = ActivityTestRule<BlankActivity>(BlankActivity::class.java)

    @Test
    fun itShowsTheInitialConfiguration() {
        // Given
        val configuration = Configuration(
            sets = 13,
            workTime = 18.seconds,
            restTime = 27.seconds
        )

        // When
        addFragment(ConfigurationFragment.withInitialConfiguration(configuration))

        // Then
        onValueTextView(R.id.setsNumberChooserView)
            .check(matches(withText("13")))
        onValueTextView(R.id.workTimeNumberChooserView)
            .check(matches(withText("18")))
        onValueTextView(R.id.restTimeNumberChooserView)
            .check(matches(withText("27")))
    }

    @Test
    fun usersCanIncrementTheSets() {
        // Given
        val configuration = Configuration(sets = 10)
        addFragment(ConfigurationFragment.withInitialConfiguration(configuration))

        // When
        onIncrementView(R.id.setsNumberChooserView)
            .perform(click())

        // Then
        onValueTextView(R.id.setsNumberChooserView)
            .check(matches(withText("11")))
    }

    @Test
    fun usersCanDecrementTheSets() {
        // Given
        val configuration = Configuration(sets = 10)
        addFragment(ConfigurationFragment.withInitialConfiguration(configuration))

        // When
        onDecrementView(R.id.setsNumberChooserView)
            .perform(click())

        // Then
        onValueTextView(R.id.setsNumberChooserView)
            .check(matches(withText("9")))
    }

    @Test
    fun usersCanIncrementTheWorkTime() {
        // Given
        val configuration = Configuration(workTime = 20.seconds)
        addFragment(ConfigurationFragment.withInitialConfiguration(configuration))

        // When
        onIncrementView(R.id.workTimeNumberChooserView)
            .perform(click())

        // Then
        onValueTextView(R.id.workTimeNumberChooserView)
            .check(matches(withText("21")))
    }

    @Test
    fun usersCanDecrementTheWorkTime() {
        // Given
        val configuration = Configuration(workTime = 20.seconds)
        addFragment(ConfigurationFragment.withInitialConfiguration(configuration))

        // When
        onDecrementView(R.id.workTimeNumberChooserView)
            .perform(click())

        // Then
        onValueTextView(R.id.workTimeNumberChooserView)
            .check(matches(withText("19")))
    }

    @Test
    fun usersCanIncrementTheRestTime() {
        // Given
        val configuration = Configuration(restTime = 30.seconds)
        addFragment(ConfigurationFragment.withInitialConfiguration(configuration))

        // When
        onIncrementView(R.id.restTimeNumberChooserView)
            .perform(click())

        // Then
        onValueTextView(R.id.restTimeNumberChooserView)
            .check(matches(withText("31")))
    }

    @Test
    fun usersCanDecrementTheRestTime() {
        // Given
        val configuration = Configuration(restTime = 30.seconds)
        addFragment(ConfigurationFragment.withInitialConfiguration(configuration))

        // When
        onDecrementView(R.id.restTimeNumberChooserView)
            .perform(click())

        // Then
        onValueTextView(R.id.restTimeNumberChooserView)
            .check(matches(withText("29")))
    }

    private fun addFragment(fragment: ConfigurationFragment) {
        activityTestRule.runOnUiThread {
            activityTestRule.activity.supportFragmentManager.beginTransaction()
                .add(android.R.id.content, fragment)
                .commit()
        }
    }

    private fun onValueTextView(numberChooserViewId: Int) =
        onView(
            allOf(
                isDescendantOfA(withId(numberChooserViewId)),
                withId(R.id.valueTextView)
            )
        )

    private fun onIncrementView(numberChooserViewId: Int) =
        onView(
            allOf(
                isDescendantOfA(withId(numberChooserViewId)),
                withId(R.id.incrementTextView)
            )
        )

    private fun onDecrementView(numberChooserViewId: Int) =
        onView(
            allOf(
                isDescendantOfA(withId(numberChooserViewId)),
                withId(R.id.decrementTextView)
            )
        )

}