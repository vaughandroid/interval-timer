package me.vaughandroid.intervaltimer.configuration.domain

import com.google.common.truth.Truth.assertThat
import me.vaughandroid.intervaltimer.time.hours
import me.vaughandroid.intervaltimer.time.seconds
import org.junit.Test

class ConfigurationModelTests {

    @Test
    fun `sets can't be less than 1`() {
        // Given
        val model = ConfigurationModel()
        val configuration = Configuration(sets = 0)

        // When
        model.updateConfiguration(configuration)

        // Then
        val currentConfiguration : Configuration = model.currentConfiguration
        assertThat(currentConfiguration.sets).isEqualTo(1)
    }

    @Test
    fun `sets can't be greater than 99`() {
        // Given
        val model = ConfigurationModel()
        val configuration = Configuration(sets = 100)

        // When
        model.updateConfiguration(configuration)

        // Then
        val currentConfiguration : Configuration = model.currentConfiguration
        assertThat(currentConfiguration.sets).isEqualTo(99)
    }

    @Test
    fun `work time can't be less than 1 second`() {
        // Given
        val model = ConfigurationModel()
        val configuration = Configuration(workTime = 0.seconds)

        // When
        model.updateConfiguration(configuration)

        // Then
        val currentConfiguration : Configuration = model.currentConfiguration
        assertThat(currentConfiguration.workTime).isEqualTo(1.seconds)
    }

    @Test
    fun `work time can't be more than 1 hour`() {
        // Given
        val model = ConfigurationModel()
        val configuration = Configuration(workTime = 1.hours + 1.seconds)

        // When
        model.updateConfiguration(configuration)

        // Then
        val currentConfiguration : Configuration = model.currentConfiguration
        assertThat(currentConfiguration.workTime).isEqualTo(1.hours)
    }

    @Test
    fun `rest time can't be less than 1 second`() {
        // Given
        val model = ConfigurationModel()
        val configuration = Configuration(restTime = 0.seconds)

        // When
        model.updateConfiguration(configuration)

        // Then
        val currentConfiguration : Configuration = model.currentConfiguration
        assertThat(currentConfiguration.restTime).isEqualTo(1.seconds)
    }

    @Test
    fun `rest time can't be more than 1 hour`() {
        // Given
        val model = ConfigurationModel()
        val configuration = Configuration(restTime = 1.hours + 1.seconds)

        // When
        model.updateConfiguration(configuration)

        // Then
        val currentConfiguration : Configuration = model.currentConfiguration
        assertThat(currentConfiguration.restTime).isEqualTo(1.hours)
    }
}