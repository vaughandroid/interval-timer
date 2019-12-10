package me.vaughandroid.intervaltimer.timer.domain

import com.google.common.truth.Truth.assertThat
import me.vaughandroid.intervaltimer.configuration.data.FakeConfigurationStore
import me.vaughandroid.intervaltimer.configuration.domain.Configuration
import me.vaughandroid.intervaltimer.time.TestTimeProvider
import me.vaughandroid.intervaltimer.time.minutes
import org.junit.Test

class TimerModel_ReadyStateTests {

    @Test // TODO: Update
    fun `current segment time remaining uses the work time`() {
        // Given
        val testTimeProvider = TestTimeProvider()
        val storedConfiguration = Configuration(workTime = 2.minutes)
        val model = TimerModel(FakeConfigurationStore(storedConfiguration), testTimeProvider)

        // When
        model.enterState(TimerState.READY, RunningState.PAUSED)

        // Then
        assertThat(model.currentSegmentTimeRemaining).isEqualTo(2.minutes)
    }

    @Test // TODO: Remove
    fun `time remaining is not updated as time passes`() {
        // Given
        val testTimeProvider = TestTimeProvider()
        val storedConfiguration = Configuration(workTime = 3.minutes)
        val model = TimerModel(FakeConfigurationStore(storedConfiguration), testTimeProvider)
        model.enterState(TimerState.READY, RunningState.PAUSED)

        // When
        testTimeProvider.advanceTime(1.minutes)

        // Then
        assertThat(model.currentSegmentTimeRemaining).isEqualTo(3.minutes)
    }

    @Test
    fun `starting enters the working state with the timer running`() {
        // Given
        val testTimeProvider = TestTimeProvider()
        val storedConfiguration = Configuration(workTime = 2.minutes)
        val model = TimerModel(FakeConfigurationStore(storedConfiguration), testTimeProvider)
        model.enterState(TimerState.READY, RunningState.PAUSED)

        // When
        model.start()
        testTimeProvider.advanceTime(1.minutes)

        // Then
        assertThat(model.currentTimerState).isEqualTo(TimerState.WORK)
        assertThat(model.runningState).isEqualTo(RunningState.RUNNING)
        assertThat(model.currentSegmentTimeRemaining).isEqualTo(1.minutes)
    }

}
