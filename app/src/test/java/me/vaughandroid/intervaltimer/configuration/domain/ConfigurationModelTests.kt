package me.vaughandroid.intervaltimer.configuration.domain

import com.google.common.truth.Truth.assertThat
import me.vaughandroid.intervaltimer.time.hours
import me.vaughandroid.intervaltimer.time.seconds
import org.junit.Test

class ConfigurationModelTests {

    @Test
    fun `sets can't be less than 1`() {
        // Given
        val model = ConfigurationModel(
            Configuration(sets = 1)
        )

        // When
        model.decrementSets()

        // Then
        val currentConfiguration : Configuration = model.currentConfiguration
        assertThat(currentConfiguration.sets).isEqualTo(1)
    }

    @Test
    fun `sets can't be greater than 99`() {
        // Given
        val model = ConfigurationModel(
            Configuration(sets = 99)
        )

        // When
        model.incrementSets()

        // Then
        val currentConfiguration : Configuration = model.currentConfiguration
        assertThat(currentConfiguration.sets).isEqualTo(99)
    }

    @Test
    fun `work time can't be less than 1 second`() {
        // Given
        val model = ConfigurationModel(
            Configuration(workTime = 1.seconds)
        )

        // When
        model.decrementWorkTime()

        // Then
        val currentConfiguration : Configuration = model.currentConfiguration
        assertThat(currentConfiguration.workTime).isEqualTo(1.seconds)
    }

    @Test
    fun `work time can't be more than 1 hour`() {
        // Given
        val oneHourAsSeconds = 1.hours
        val model = ConfigurationModel(
            Configuration(workTime = oneHourAsSeconds)
        )

        // When
        model.incrementWorkTime()

        // Then
        val currentConfiguration : Configuration = model.currentConfiguration
        assertThat(currentConfiguration.workTime).isEqualTo(oneHourAsSeconds)
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
        assertThat(currentConfiguration.restTime).isEqualTo(1.seconds)
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
        assertThat(currentConfiguration.restTime).isEqualTo(oneHourAsSeconds)
    }
}