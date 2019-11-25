package me.vaughandroid.intervaltimer.configuration.view

import com.google.common.truth.Truth.assertThat
import me.vaughandroid.intervaltimer.configuration.domain.Configuration
import me.vaughandroid.intervaltimer.time.minutes
import me.vaughandroid.intervaltimer.time.seconds
import org.junit.Test

class ConfigurationPresenterTests {

    @Test
    fun `configuration values should be transformed correctly`() {
        // Given
        val configuration = Configuration(
            sets = 12,
            workTime = 2.minutes + 30.seconds,
            restTime = 10.minutes
        )
        val expectedViewData =
            ConfigurationViewData(
                setsText = "12",
                workTimeText = "2:30",
                restTimeText = "10:00"
            )

        // When
        val receivedViewData = ConfigurationPresenter.transform(configuration)

        // Then
        assertThat(receivedViewData).isEqualTo(expectedViewData)
    }

}


