package me.vaughandroid.intervaltimer.configuration.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.google.common.truth.Truth.assertThat
import me.vaughandroid.intervaltimer.BlankActivity
import me.vaughandroid.intervaltimer.IntervalTimerApplication
import me.vaughandroid.intervaltimer.R
import me.vaughandroid.intervaltimer.Screen
import me.vaughandroid.intervaltimer.configuration.data.StubConfigurationStore
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
class ConfigurationFragmentTests {

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
    ): ConfigurationFragment {
        val stubConfigurationStore = StubConfigurationStore(configuration)
        val appContainer = AppDependencies(stubConfigurationStore)
        intervalTimerApplication.setAppContainer(appContainer)
        return ConfigurationFragment()
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
        onValueTextView(R.id.setsNumberChooserView)
            .check(matches(withText("13")))
        onValueTextView(R.id.workTimeNumberChooserView)
            .check(matches(withText("1:18")))
        onValueTextView(R.id.restTimeNumberChooserView)
            .check(matches(withText("32:27")))
    }

    @Test
    fun usersCanIncrementTheSets() {
        // Given
        val configuration =
            Configuration(sets = 10)
        addFragment(createFragmentWithInitialConfiguration(configuration))

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
        val configuration =
            Configuration(sets = 10)
        addFragment(createFragmentWithInitialConfiguration(configuration))

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
        val configuration =
            Configuration(workTime = 20.seconds)
        addFragment(createFragmentWithInitialConfiguration(configuration))

        // When
        onIncrementView(R.id.workTimeNumberChooserView)
            .perform(click())

        // Then
        onValueTextView(R.id.workTimeNumberChooserView)
            .check(matches(withText("0:21")))
    }

    @Test
    fun usersCanDecrementTheWorkTime() {
        // Given
        val configuration =
            Configuration(workTime = 20.seconds)
        addFragment(createFragmentWithInitialConfiguration(configuration))

        // When
        onDecrementView(R.id.workTimeNumberChooserView)
            .perform(click())

        // Then
        onValueTextView(R.id.workTimeNumberChooserView)
            .check(matches(withText("0:19")))
    }

    @Test
    fun usersCanIncrementTheRestTime() {
        // Given
        val configuration =
            Configuration(restTime = 30.seconds)
        addFragment(createFragmentWithInitialConfiguration(configuration))

        // When
        onIncrementView(R.id.restTimeNumberChooserView)
            .perform(click())

        // Then
        onValueTextView(R.id.restTimeNumberChooserView)
            .check(matches(withText("0:31")))
    }

    @Test
    fun usersCanDecrementTheRestTime() {
        // Given
        val configuration =
            Configuration(restTime = 30.seconds)
        addFragment(createFragmentWithInitialConfiguration(configuration))

        // When
        onDecrementView(R.id.restTimeNumberChooserView)
            .perform(click())

        // Then
        onValueTextView(R.id.restTimeNumberChooserView)
            .check(matches(withText("0:29")))
    }

    @Test
    fun doneButtonNavigatesToTheTimerScreen() {
        // Given
        val configuration = Configuration(
            sets = 13,
            workTime = 17.seconds,
            restTime = 19.seconds
        )
        val fragment = createFragmentWithInitialConfiguration(configuration)
        var receivedScreen: Screen? = null
        fragment.navigationEventHandler = { navigationEvent ->
            receivedScreen = navigationEvent
        }
        addFragment(fragment)

        // When
        onView(withId(R.id.doneButton))
            .perform(click())

        // Then
        assertThat(receivedScreen).isEqualTo(Screen.TIMER)
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