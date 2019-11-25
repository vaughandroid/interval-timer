package me.vaughandroid.intervaltimer.configuration.domain

import com.google.common.truth.Truth
import me.vaughandroid.intervaltimer.configuration.domain.Configuration
import me.vaughandroid.intervaltimer.configuration.domain.ConfigurationModel
import me.vaughandroid.intervaltimer.time.hours
import me.vaughandroid.intervaltimer.time.seconds
import org.junit.Test

class RestTimeConfigurationTests {

    @Test
    fun `rest time can be incremented`() {
        // Given
        val model = ConfigurationModel(
            Configuration(restTime = 12.seconds)
        )

        // When
        model.incrementRestTime()

        // Then
        val currentConfiguration : Configuration = model.currentConfiguration
        Truth.assertThat(currentConfiguration.restTime).isEqualTo(13.seconds)
    }

    @Test
    fun `rest time can be decremented`() {
        // Given
        val model = ConfigurationModel(
            Configuration(restTime = 30.seconds)
        )

        // When
        model.decrementRestTime()

        // Then
        val currentConfiguration : Configuration = model.currentConfiguration
        Truth.assertThat(currentConfiguration.restTime).isEqualTo(29.seconds)
    }

    @Test
    fun `rest time can't be less than 1 second`() {
        // Given
        val model = ConfigurationModel(
            Configuration(restTime = 1.seconds)
        )

        // When
        model.decrementRestTime()

        // Then
        val currentConfiguration : Configuration = model.currentConfiguration
        Truth.assertThat(currentConfiguration.restTime).isEqualTo(1.seconds)
    }

    @Test
    fun `rest time can't be more than 1 hour`() {
        // Given
        val oneHourAsSeconds = 1.hours
        val model = ConfigurationModel(
            Configuration(restTime = oneHourAsSeconds)
        )

        // When
        model.incrementRestTime()

        // Then
        val currentConfiguration : Configuration = model.currentConfiguration
        Truth.assertThat(currentConfiguration.restTime).isEqualTo(oneHourAsSeconds)
    }

}