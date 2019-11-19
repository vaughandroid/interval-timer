package me.vaughandroid.intervaltimer.configuration

import com.google.common.truth.Truth
import me.vaughandroid.intervaltimer.time.hours
import me.vaughandroid.intervaltimer.time.seconds
import org.junit.Test

class WorkTimeConfigurationTests {

    @Test
    fun `work time can be incremented`() {
        // Given
        val model = ConfigurationModel(Configuration(workTime = 12.seconds))

        // When
        model.incrementWorkTime()

        // Then
        val currentConfiguration : Configuration = model.currentConfiguration
        Truth.assertThat(currentConfiguration.workTime).isEqualTo(13.seconds)
    }

    @Test
    fun `work time can be decremented`() {
        // Given
        val model = ConfigurationModel(Configuration(workTime = 30.seconds))

        // When
        model.decrementWorkTime()

        // Then
        val currentConfiguration : Configuration = model.currentConfiguration
        Truth.assertThat(currentConfiguration.workTime).isEqualTo(29.seconds)
    }

    @Test
    fun `work time can't be less than 1 second`() {
        // Given
        val model = ConfigurationModel(Configuration(workTime = 1.seconds))

        // When
        model.decrementWorkTime()

        // Then
        val currentConfiguration : Configuration = model.currentConfiguration
        Truth.assertThat(currentConfiguration.workTime).isEqualTo(1.seconds)
    }

    @Test
    fun `work time can't be more than 1 hour`() {
        // Given
        val oneHourAsSeconds = 1.hours
        val model = ConfigurationModel(Configuration(workTime = oneHourAsSeconds))

        // When
        model.incrementWorkTime()

        // Then
        val currentConfiguration : Configuration = model.currentConfiguration
        Truth.assertThat(currentConfiguration.workTime).isEqualTo(oneHourAsSeconds)
    }

}