package me.vaughandroid.intervaltimer.widgets

import android.view.ViewGroup
import androidx.test.annotation.UiThreadTest
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.google.common.truth.Truth.assertThat
import me.vaughandroid.intervaltimer.BlankActivity
import me.vaughandroid.intervaltimer.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class NumberChooserViewTests {

    @get:Rule
    val activityTestRule = ActivityTestRule<BlankActivity>(BlankActivity::class.java)

    private lateinit var numberChooserView: NumberChooserView

    @UiThreadTest
    @Before
    fun setup() {
        val activity = activityTestRule.activity

        numberChooserView = NumberChooserView(activity)

        val contentView = activity.findViewById(android.R.id.content) as ViewGroup
        contentView.addView(numberChooserView)
    }

    @Test
    fun title_is_displayed() {
        // Given
        val titleText = "Title text"

        // When
        numberChooserView.title = titleText

        // Then
        onView(withId(R.id.titleTextView))
            .check(matches(withText(titleText)))
    }

    @Test
    fun value_is_displayed() {
        // Given
        val value = "1234"

        // When
        numberChooserView.value = value

        // Then
        onView(withId(R.id.valueTextView))
            .check(matches(withText("1234")))
    }

    @Test
    fun when_increment_is_pressed_event_is_emitted_to_the_increment_listener() {
        // Given
        val value = "1234"
        numberChooserView.value = value
        var incrementCalled = false
        var receivedCurrentValue = ""
        numberChooserView.incrementListener = { currentValue ->
            incrementCalled = true
            receivedCurrentValue = currentValue
        }

        // When
        onView(withId(R.id.incrementTextView))
            .perform(click())

        // Then
        assertThat(incrementCalled).isTrue()
        assertThat(receivedCurrentValue).isEqualTo(value)
    }

    @Test
    fun when_decrement_is_pressed_event_is_emitted_to_the_decrement_listener() {
        // Given
        val value = "1234"
        numberChooserView.value = value
        var incrementCalled = false
        var receivedCurrentValue = ""
        numberChooserView.decrementListener = { currentValue ->
            incrementCalled = true
            receivedCurrentValue = currentValue
        }

        // When
        onView(withId(R.id.decrementTextView))
            .perform(click())

        // Then
        assertThat(incrementCalled).isTrue()
        assertThat(receivedCurrentValue).isEqualTo(value)
    }
}