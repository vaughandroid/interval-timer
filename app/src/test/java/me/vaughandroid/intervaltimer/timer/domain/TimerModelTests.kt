package me.vaughandroid.intervaltimer.timer.domain

import com.google.common.truth.Truth.assertThat
import me.vaughandroid.intervaltimer.configuration.data.FakeConfigurationStore
import me.vaughandroid.intervaltimer.configuration.domain.Configuration
import me.vaughandroid.intervaltimer.time.SystemTimeProvider
import me.vaughandroid.intervaltimer.time.TestTimeProvider
import me.vaughandroid.intervaltimer.time.minutes
import me.vaughandroid.intervaltimer.time.seconds
import org.junit.Test

class TimerModelTests {

    @Test
    fun `initial values are read from the configuration store`() {
        // Given
        val storedConfiguration = Configuration(
            sets = 12,
            workTime = 37.seconds,
            restTime = 43.seconds
        )
        val stubStore = FakeConfigurationStore(storedConfiguration)

        // When
        val model = TimerModel(stubStore, SystemTimeProvider())

        // Then
        assertThat(model.totalSets).isEqualTo(12)
        assertThat(model.currentWorkTime).isEqualTo(37.seconds)
    }

    @Test
    fun `when not started, values are not updated as time passes`() {
        // Given
        val testTimeProvider = TestTimeProvider()
        val storedConfiguration = Configuration(
            workTime = 2.minutes,
            restTime = 2.minutes
        )
        val model = TimerModel(FakeConfigurationStore(storedConfiguration), testTimeProvider)

        // When
        testTimeProvider.advanceTime(1.minutes)

        // Then
        assertThat(model.currentWorkTime).isEqualTo(2.minutes)
    }

    @Test
    fun `when started, work time is updated as time passes`() {
        // Given
        val testTimeProvider = TestTimeProvider()
        val storedConfiguration = Configuration(
            workTime = 2.minutes,
            restTime = 2.minutes
        )
        val model = TimerModel(FakeConfigurationStore(storedConfiguration), testTimeProvider)

        // When
        model.start()
        testTimeProvider.advanceTime(1.minutes)

        // Then
        assertThat(model.currentWorkTime).isEqualTo(1.minutes)
    }

    @Test
    fun `when a started timer is paused, values are no longer updated as time passes`() {
        // Given
        val testTimeProvider = TestTimeProvider()
        val storedConfiguration = Configuration(
            workTime = 1.minutes,
            restTime = 1.minutes
        )
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
    fun `starting a paused timer resumes where it left off`() {
        // Given
        val testTimeProvider = TestTimeProvider()
        val storedConfiguration = Configuration(
            workTime = 1.minutes,
            restTime = 1.minutes
        )
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

    @Test
    fun `after work time has elapsed it starts counting down rest time`() {
        // Given
        val testTimeProvider = TestTimeProvider()
        val storedConfiguration = Configuration(
            workTime = 1.minutes,
            restTime = 1.minutes
        )
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
