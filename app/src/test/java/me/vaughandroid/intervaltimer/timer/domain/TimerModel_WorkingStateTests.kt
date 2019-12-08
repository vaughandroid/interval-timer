package me.vaughandroid.intervaltimer.timer.domain

import com.google.common.truth.Truth.assertThat
import me.vaughandroid.intervaltimer.configuration.data.FakeConfigurationStore
import me.vaughandroid.intervaltimer.configuration.domain.Configuration
import me.vaughandroid.intervaltimer.time.Duration
import me.vaughandroid.intervaltimer.time.TestTimeProvider
import me.vaughandroid.intervaltimer.time.minutes
import me.vaughandroid.intervaltimer.time.seconds
import org.junit.Test

class TimerModel_WorkingStateTests {

    @Test
    fun `when running, time remaining is updated as time passes`() {
        // Given
        val testTimeProvider = TestTimeProvider()
        val storedConfiguration = Configuration(workTime = 2.minutes)
        val model = TimerModel(FakeConfigurationStore(storedConfiguration), testTimeProvider)

        // When
        model.start()
        testTimeProvider.advanceTime(1.minutes)

        // Then
        assertThat(model.currentSegmentTimeRemaining).isEqualTo(1.minutes)
    }

    @Test
    fun `when running, pausing pauses the timer`() {
        // Given
        val testTimeProvider = TestTimeProvider()
        val storedConfiguration = Configuration(workTime = 1.minutes)
        val model = TimerModel(FakeConfigurationStore(storedConfiguration), testTimeProvider)
        model.start()

        // When
        model.pause()

        // Then
        assertThat(model.currentState).isEqualTo(TimerState.WORK_PAUSED)
    }

    @Test
    fun `when paused, time remaining is not updated as time passes`() {
        // Given
        val testTimeProvider = TestTimeProvider()
        val storedConfiguration = Configuration(workTime = 1.minutes)
        val model = TimerModel(FakeConfigurationStore(storedConfiguration), testTimeProvider)
        model.start()
        testTimeProvider.advanceTime(10.seconds)

        // When
        model.pause()
        testTimeProvider.advanceTime(10.seconds)

        // Then
        assertThat(model.currentSegmentTimeRemaining).isEqualTo(50.seconds)
    }

    @Test
    fun `when paused, starting starts the timer running and the working time resumes where it left off`() {
        // Given
        val testTimeProvider = TestTimeProvider()
        val storedConfiguration = Configuration(workTime = 1.minutes)
        val model = TimerModel(FakeConfigurationStore(storedConfiguration), testTimeProvider)
        model.start()
        testTimeProvider.advanceTime(10.seconds)
        model.pause()

        // When
        model.start()
        testTimeProvider.advanceTime(10.seconds)

        // Then
        assertThat(model.currentState).isEqualTo(TimerState.WORK_RUNNING)
        assertThat(model.currentSegmentTimeRemaining).isEqualTo(40.seconds)
    }

    @Test
    fun `when running, as soon as work time elapses it enters the resting state with the full amount of rest time`() {
        // Given
        val testTimeProvider = TestTimeProvider()
        val storedConfiguration = Configuration(
            workTime = 2.minutes,
            restTime = 1.minutes
        )
        val model = TimerModel(FakeConfigurationStore(storedConfiguration), testTimeProvider)

        // When
        model.start()
        testTimeProvider.advanceTime(2.minutes)

        // Then
        assertThat(model.currentState).isEqualTo(TimerState.REST_RUNNING)
        assertThat(model.currentSegmentTimeRemaining).isEqualTo(1.minutes)
    }

    @Test
    fun `when transitioning to the next state, spare elapsed time is carried over ans subtracted from the rest time`() {
        // Given
        val testTimeProvider = TestTimeProvider()
        val storedConfiguration = Configuration(
            workTime = 10.seconds,
            restTime = 10.seconds
        )
        val model = TimerModel(FakeConfigurationStore(storedConfiguration), testTimeProvider)

        // When
        model.start()
        val spareTime = Duration(123)
        testTimeProvider.advanceTime(10.seconds + spareTime)

        // Then
        assertThat(model.currentSegmentTimeRemaining).isEqualTo(10.seconds - spareTime)
    }

}
