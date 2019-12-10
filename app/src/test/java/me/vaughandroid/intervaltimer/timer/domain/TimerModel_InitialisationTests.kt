package me.vaughandroid.intervaltimer.timer.domain

import com.google.common.truth.Truth.assertThat
import me.vaughandroid.intervaltimer.configuration.data.FakeConfigurationStore
import me.vaughandroid.intervaltimer.configuration.domain.Configuration
import me.vaughandroid.intervaltimer.time.SystemTimeProvider
import org.junit.Test

class TimerModel_InitialisationTests {

    @Test
    fun `when initialised, it is paused in the ready state`() {
        // When
        val model = TimerModel(FakeConfigurationStore(), SystemTimeProvider())

        // Then
        assertThat(model.currentTimerState).isEqualTo(TimerState.READY)
        assertThat(model.runningState).isEqualTo(RunningState.PAUSED)
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

}
