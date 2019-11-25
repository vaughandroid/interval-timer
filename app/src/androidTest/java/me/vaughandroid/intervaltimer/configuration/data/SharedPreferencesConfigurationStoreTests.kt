package me.vaughandroid.intervaltimer.configuration.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import me.vaughandroid.intervaltimer.configuration.domain.Configuration
import me.vaughandroid.intervaltimer.time.minutes
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class SharedPreferencesConfigurationStoreTests {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setUp() {
        context.deleteSharedPreferences(SharedPreferencesConfigurationStore.SHARED_PREFS_NAME)
    }

    @After
    fun tearDown() {
        context.deleteSharedPreferences(SharedPreferencesConfigurationStore.SHARED_PREFS_NAME)
    }

    @Test
    fun when_nothing_has_been_saved_it_returns_a_default_configuration() {
        // Given
        val store =
            SharedPreferencesConfigurationStore(
                context
            )
        
        // When
        val configuration = store.getConfiguration()

        // Then
        assertThat(configuration).isEqualTo(Configuration())
    }

    @Test
    fun a_stored_value_can_be_retrieved() {
        // Given
        val store =
            SharedPreferencesConfigurationStore(
                context
            )
        val storedConfiguration = Configuration(
            sets = 11,
            workTime = 1.minutes,
            restTime = 2.minutes
        )
        store.putConfiguration(storedConfiguration)

        // When
        val retrievedConfiguration = store.getConfiguration()

        // Then
        assertThat(retrievedConfiguration).isEqualTo(storedConfiguration)
    }

    @Test
    fun a_stored_value_can_be_retrieved_by_a_different_store_instance() {
        // Given
        val storeA =
            SharedPreferencesConfigurationStore(
                context
            )
        val storedConfiguration = Configuration(
            sets = 11,
            workTime = 1.minutes,
            restTime = 2.minutes
        )
        storeA.putConfiguration(storedConfiguration)
        val storeB =
            SharedPreferencesConfigurationStore(
                context
            )

        // When
        val retrievedConfiguration = storeB.getConfiguration()

        // Then
        assertThat(retrievedConfiguration).isEqualTo(storedConfiguration)
    }

}

