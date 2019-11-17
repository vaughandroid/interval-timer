package me.vaughandroid.intervaltimer.configuration

import androidx.test.espresso.Espresso.onView
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
        onView(
            allOf(
                isDescendantOfA(withId(R.id.setsNumberChooserView)),
                withId(R.id.valueTextView)
            )
        )
            .check(matches(withText("13")))
        onView(
            allOf(
                isDescendantOfA(withId(R.id.workTimeNumberChooserView)),
                withId(R.id.valueTextView)
            )
        )
            .check(matches(withText("18")))
        onView(
            allOf(
                isDescendantOfA(withId(R.id.restTimeNumberChooserView)),
                withId(R.id.valueTextView)
            )
        )
            .check(matches(withText("27")))
    }

    private fun addFragment(fragment: ConfigurationFragment) {
        activityTestRule.runOnUiThread {
            activityTestRule.activity.supportFragmentManager.beginTransaction()
                .add(android.R.id.content, fragment)
                .commit()
        }
    }

}