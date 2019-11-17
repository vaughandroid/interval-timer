package me.vaughandroid.intervaltimer.configuration

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class SetsConfigurationTests {

    @Test
    fun `sets can be incremented`() {
        // Given
        val model = ConfigurationModel(Configuration(sets = 6))

        // When
        model.incrementSets()

        // Then
        val currentConfiguration : Configuration = model.currentConfiguration
        assertThat(currentConfiguration.sets).isEqualTo(7)
    }

    @Test
    fun `sets can be decremented`() {
        // Given
        val model = ConfigurationModel(Configuration(sets = 22))

        // When
        model.decrementSets()

        // Then
        val currentConfiguration : Configuration = model.currentConfiguration
        assertThat(currentConfiguration.sets).isEqualTo(21)
    }

    @Test
    fun `sets can't be greater than 99`() {
        // Given
        val model = ConfigurationModel(Configuration(sets = 99))

        // When
        model.incrementSets()

        // Then
        val currentConfiguration : Configuration = model.currentConfiguration
        assertThat(currentConfiguration.sets).isEqualTo(99)
    }

    @Test
    fun `sets can't be less than 1`() {
        // Given
        val model = ConfigurationModel(Configuration(sets = 1))

        // When
        model.decrementSets()

        // Then
        val currentConfiguration : Configuration = model.currentConfiguration
        assertThat(currentConfiguration.sets).isEqualTo(1)
    }

}

class ConfigurationModel(initialConfiguration: Configuration) {

    var currentConfiguration: Configuration = initialConfiguration
        private set

    fun decrementSets() {
        val newSets = (currentConfiguration.sets - 1).coerceAtLeast(1)
        currentConfiguration = currentConfiguration.copy(sets = newSets)
    }

    fun incrementSets() {
        val newSets = (currentConfiguration.sets + 1).coerceAtMost(99)
        currentConfiguration = currentConfiguration.copy(sets = newSets)
    }

}

data class Configuration(
    val sets: Int
)
