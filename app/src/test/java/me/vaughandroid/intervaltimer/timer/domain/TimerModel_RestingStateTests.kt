package me.vaughandroid.intervaltimer.timer.domain

import com.google.common.truth.Truth.assertThat
import me.vaughandroid.intervaltimer.configuration.data.FakeConfigurationStore
import me.vaughandroid.intervaltimer.configuration.domain.Configuration
import me.vaughandroid.intervaltimer.time.Duration
import me.vaughandroid.intervaltimer.time.TestTimeProvider
import me.vaughandroid.intervaltimer.time.minutes
import me.vaughandroid.intervaltimer.time.seconds
import org.junit.Test

class TimerModel_RestingStateTests {

    @Test
    fun `when running, time remaining is updated as time passes`() {
        // Given
        val testTimeProvider = TestTimeProvider()
        val storedConfiguration = Configuration(restTime = 4.minutes)
        val model = TimerModel(FakeConfigurationStore(storedConfiguration), testTimeProvider)
        model.enterState(TimerState.REST, RunningState.RUNNING)

        // When
        testTimeProvider.advanceTime(1.minutes)

        // Then
        assertThat(model.currentSegmentTimeRemaining).isEqualTo(3.minutes)
    }

    @Test
    fun `when running, pausing enters the resting paused state`() {
        // Given
        val testTimeProvider = TestTimeProvider()
        val storedConfiguration = Configuration(restTime = 1.minutes)
        val model = TimerModel(FakeConfigurationStore(storedConfiguration), testTimeProvider)
        model.enterState(TimerState.REST, RunningState.RUNNING)

        // When
        model.pause()

        // Then
        assertThat(model.currentTimerState).isEqualTo(TimerState.REST)
        assertThat(model.runningState).isEqualTo(RunningState.PAUSED)
    }

    @Test
    fun `when paused, time remaining is not updated as time passes`() {
        // Given
        val testTimeProvider = TestTimeProvider()
        val storedConfiguration = Configuration(restTime = 1.minutes)
        val model = TimerModel(FakeConfigurationStore(storedConfiguration), testTimeProvider)
        model.enterState(TimerState.REST, RunningState.PAUSED)

        // When
        testTimeProvider.advanceTime(10.seconds)

        // Then
        assertThat(model.currentSegmentTimeRemaining).isEqualTo(1.minutes)
    }

    @Test
    fun `when paused, starting starts the timer running and the working time resumes where it left off`() {
        // Given
        val testTimeProvider = TestTimeProvider()
        val storedConfiguration = Configuration(restTime = 30.seconds)
        val model = TimerModel(FakeConfigurationStore(storedConfiguration), testTimeProvider)
        // Paused with 20 seconds remaining.
        model.enterState(TimerState.REST, RunningState.PAUSED, -(10.seconds))

        // When
        model.start()
        testTimeProvider.advanceTime(5.seconds)

        // Then
        assertThat(model.currentTimerState).isEqualTo(TimerState.REST)
        assertThat(model.runningState).isEqualTo(RunningState.RUNNING)
        assertThat(model.currentSegmentTimeRemaining).isEqualTo(15.seconds)
    }

    @Test
    fun `when running, as soon as rest time elapses it enters the work running state with the full amount of work time`() {
        // Given
        val testTimeProvider = TestTimeProvider()
        val storedConfiguration = Configuration(
            restTime = 1.minutes,
            workTime = 2.minutes
        )
        val model = TimerModel(FakeConfigurationStore(storedConfiguration), testTimeProvider)
        model.enterState(TimerState.REST, RunningState.RUNNING)

        // When
        testTimeProvider.advanceTime(1.minutes)

        // Then
        assertThat(model.currentTimerState).isEqualTo(TimerState.WORK)
        assertThat(model.runningState).isEqualTo(RunningState.RUNNING)
        assertThat(model.currentSegmentTimeRemaining).isEqualTo(2.minutes)
    }

    @Test
    fun `when transitioning to the next state, spare elapsed time is carried over ans subtracted from the rest time`() {
        // Given
        val testTimeProvider = TestTimeProvider()
        val storedConfiguration = Configuration(
            restTime = 30.seconds,
            workTime = 30.seconds
        )
        val model = TimerModel(FakeConfigurationStore(storedConfiguration), testTimeProvider)
        model.enterState(TimerState.REST, RunningState.RUNNING)

        // When
        val spareTime = Duration(123)
        testTimeProvider.advanceTime(30.seconds + spareTime)

        // Then
        assertThat(model.currentSegmentTimeRemaining).isEqualTo(30.seconds - spareTime)
    }

}
