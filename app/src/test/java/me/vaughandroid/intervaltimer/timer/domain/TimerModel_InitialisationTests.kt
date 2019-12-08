package me.vaughandroid.intervaltimer.timer.domain

import com.google.common.truth.Truth.assertThat
import me.vaughandroid.intervaltimer.configuration.data.FakeConfigurationStore
import me.vaughandroid.intervaltimer.configuration.domain.Configuration
import me.vaughandroid.intervaltimer.time.SystemTimeProvider
import me.vaughandroid.intervaltimer.time.seconds
import org.junit.Test

class TimerModel_InitialisationTests {

    @Test
    fun `when initialised, it is in the ready state`() {
        // When
        val model = TimerModel(FakeConfigurationStore(), SystemTimeProvider())

        // Then
        assertThat(model.currentState).isEqualTo(TimerState.READY)
    }

    @Test
    fun `the number of sets is read from the configuration store`() {
        // Given
        val storedConfiguration = Configuration(sets = 12)
        val stubStore = FakeConfigurationStore(storedConfiguration)

        // When
        val model = TimerModel(stubStore, SystemTimeProvider())

        // Then
        assertThat(model.totalSets).isEqualTo(12)
    }

    @Test
    fun `initial work time is read from the configuration store`() {
        // Given
        val storedConfiguration = Configuration(workTime = 37.seconds)
        val stubStore = FakeConfigurationStore(storedConfiguration)

        // When
        val model = TimerModel(stubStore, SystemTimeProvider())

        // Then
        assertThat(model.currentWorkTime).isEqualTo(37.seconds)
    }

}
