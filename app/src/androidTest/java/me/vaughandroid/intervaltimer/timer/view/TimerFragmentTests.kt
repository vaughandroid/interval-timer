package me.vaughandroid.intervaltimer.timer.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import me.vaughandroid.intervaltimer.BlankActivity
import me.vaughandroid.intervaltimer.IntervalTimerApplication
import me.vaughandroid.intervaltimer.R
import me.vaughandroid.intervaltimer.configuration.data.ConfigurationStore
import me.vaughandroid.intervaltimer.configuration.domain.Configuration
import me.vaughandroid.intervaltimer.di.AppDependencies
import me.vaughandroid.intervaltimer.time.minutes
import me.vaughandroid.intervaltimer.time.seconds
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class TimerFragmentTests {

    @get:Rule
    val activityTestRule = ActivityTestRule<BlankActivity>(BlankActivity::class.java)

    private val intervalTimerApplication =
        InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as IntervalTimerApplication

    @Before
    fun setUp() {
        intervalTimerApplication.clearAppContainer()
    }

    @After
    fun tearDown() {
        intervalTimerApplication.clearAppContainer()
    }

    private fun createFragmentWithInitialConfiguration(
        configuration: Configuration
    ): TimerFragment {
        val stubConfigurationStore = StubConfigurationStore(configuration)
        val appContainer = AppDependencies(stubConfigurationStore)
        intervalTimerApplication.setAppContainer(appContainer)
        return TimerFragment()
    }

    @Test
    fun itShowsTheInitialConfiguration() {
        // Given
        val configuration = Configuration(
            sets = 13,
            workTime = 1.minutes + 18.seconds,
            restTime = 32.minutes + 27.seconds
        )

        // When
        addFragment(createFragmentWithInitialConfiguration(configuration))

        // Then
        onView(withId(R.id.setsTextView))
            .check(matches(withText("13")))
        onView(withId(R.id.workTimeTextView))
            .check(matches(withText("1:18")))
        onView(withId(R.id.restTimeTextView))
            .check(matches(withText("32:27")))
    }

    private fun addFragment(fragment: TimerFragment) {
        activityTestRule.runOnUiThread {
            activityTestRule.activity.supportFragmentManager.beginTransaction()
                .add(android.R.id.content, fragment)
                .commit()
        }
    }

}

private class StubConfigurationStore(
    private val storedConfiguration: Configuration = Configuration()
) : ConfigurationStore {
    override fun getConfiguration(): Configuration = storedConfiguration

    override fun putConfiguration(configuration: Configuration) {}
}