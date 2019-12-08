package me.vaughandroid.intervaltimer.timer.domain

import com.google.common.truth.Truth.assertThat
import me.vaughandroid.intervaltimer.configuration.data.FakeConfigurationStore
import me.vaughandroid.intervaltimer.configuration.domain.Configuration
import me.vaughandroid.intervaltimer.time.SystemTimeProvider
import me.vaughandroid.intervaltimer.time.TestTimeProvider
import me.vaughandroid.intervaltimer.time.minutes
import me.vaughandroid.intervaltimer.time.seconds
import org.junit.Test

class TimerModel_ReadyStateTests {

    @Test
    fun `time remaining is not updated as time passes`() {
        // Given
        val testTimeProvider = TestTimeProvider()
        val storedConfiguration = Configuration(workTime = 2.minutes)
        val model = TimerModel(FakeConfigurationStore(storedConfiguration), testTimeProvider)

        // When
        testTimeProvider.advanceTime(1.minutes)

        // Then
        assertThat(model.currentWorkTime).isEqualTo(2.minutes)
    }

    @Test
    fun `starting enters the working state with the timer running`() {
        // Given
        val testTimeProvider = TestTimeProvider()
        val storedConfiguration = Configuration(workTime = 2.minutes)
        val model = TimerModel(FakeConfigurationStore(storedConfiguration), testTimeProvider)

        // When
        model.start()
        testTimeProvider.advanceTime(1.minutes)

        // Then
        assertThat(model.currentState).isEqualTo(TimerState.WORK_RUNNING)
        assertThat(model.currentWorkTime).isEqualTo(1.minutes)
    }

}
