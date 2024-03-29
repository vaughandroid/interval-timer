package me.vaughandroid.intervaltimer.timer.domain

import com.google.common.truth.Truth.assertThat
import me.vaughandroid.intervaltimer.configuration.data.FakeConfigurationStore
import me.vaughandroid.intervaltimer.configuration.domain.Configuration
import me.vaughandroid.intervaltimer.time.Duration
import me.vaughandroid.intervaltimer.time.TestTimeProvider
import me.vaughandroid.intervaltimer.time.minutes
import org.junit.Test

class TimerModel_ReadyStateTests {

    @Test
    fun `current segment time remaining is zero`() {
        // Given
        val testTimeProvider = TestTimeProvider()
        val storedConfiguration = Configuration()
        val model = TimerModel(FakeConfigurationStore(storedConfiguration), testTimeProvider)

        // When
        model.enterState(TimerState.READY, RunningState.PAUSED)

        // Then
        assertThat(model.currentSegmentTimeRemaining).isEqualTo(Duration.ZERO)
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
