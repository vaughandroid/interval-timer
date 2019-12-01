package me.vaughandroid.intervaltimer.timer.domain

import com.google.common.truth.Truth.assertThat
import me.vaughandroid.intervaltimer.configuration.data.FakeConfigurationStore
import me.vaughandroid.intervaltimer.configuration.domain.Configuration
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
        val model = TimerModel(stubStore)

        // Then
        assertThat(model.currentSets).isEqualTo(12)
        assertThat(model.currentWorkTime).isEqualTo(37.seconds)
        assertThat(model.currentRestTime).isEqualTo(43.seconds)
    }

}
