package me.vaughandroid.intervaltimer.timer.domain

import com.google.common.truth.Truth.assertThat
import me.vaughandroid.intervaltimer.configuration.data.FakeConfigurationStore
import me.vaughandroid.intervaltimer.configuration.domain.Configuration
import me.vaughandroid.intervaltimer.time.SystemTimeProvider
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
        assertThat(model.currentWorkTime).isEqualTo(1.minutes)
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
        assertThat(model.currentWorkTime).isEqualTo(50.seconds)
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
        assertThat(model.currentWorkTime).isEqualTo(40.seconds)
    }

    @Test
    fun `when running, after work time has elapsed it enters the resting state with the timer running`() {
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
        assertThat(model.currentWorkTime).isEqualTo(40.seconds)
    }

}
