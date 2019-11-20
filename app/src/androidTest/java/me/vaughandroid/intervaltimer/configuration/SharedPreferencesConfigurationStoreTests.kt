package me.vaughandroid.intervaltimer.configuration

import android.content.Context
import androidx.core.content.edit
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import me.vaughandroid.intervaltimer.configuration.SharedPreferencesConfigurationStore.Companion.SHARED_PREFS_NAME
import me.vaughandroid.intervaltimer.time.Duration
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
        context.deleteSharedPreferences(SHARED_PREFS_NAME)
    }

    @After
    fun tearDown() {
        context.deleteSharedPreferences(SHARED_PREFS_NAME)
    }

    @Test
    fun when_nothing_has_been_saved_it_returns_a_default_configuration() {
        // Given
        val store = SharedPreferencesConfigurationStore(context)
        
        // When
        val configuration = store.getConfiguration()

        // Then
        assertThat(configuration).isEqualTo(Configuration())
    }

    @Test
    fun a_stored_value_can_be_retrieved() {
        // Given
        val store = SharedPreferencesConfigurationStore(context)
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
        val storeA = SharedPreferencesConfigurationStore(context)
        val storedConfiguration = Configuration(
            sets = 11,
            workTime = 1.minutes,
            restTime = 2.minutes
        )
        storeA.putConfiguration(storedConfiguration)
        val storeB = SharedPreferencesConfigurationStore(context)

        // When
        val retrievedConfiguration = storeB.getConfiguration()

        // Then
        assertThat(retrievedConfiguration).isEqualTo(storedConfiguration)
    }

}

class SharedPreferencesConfigurationStore(
    context: Context
) : ConfigurationStore {

    companion object {
        internal const val SHARED_PREFS_NAME = "configuration_store"

        private const val KEY_SETS = "sets"
        private const val KEY_WORK_TIME = "work_time"
        private const val KEY_REST_TIME = "rest_time"

    }

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    override fun getConfiguration(): Configuration {
        return if (sharedPreferences.contains(KEY_SETS)) {
            Configuration(
                sets = sharedPreferences.getInt(KEY_SETS, 1),
                workTime = Duration(sharedPreferences.getInt(KEY_WORK_TIME, 1000)),
                restTime = Duration(sharedPreferences.getInt(KEY_REST_TIME, 1000))
            )
        } else Configuration()
    }

    override fun putConfiguration(configuration: Configuration) {
        sharedPreferences.edit {
            putInt(KEY_SETS, configuration.sets)
            putInt(KEY_WORK_TIME, configuration.workTime.millis)
            putInt(KEY_REST_TIME, configuration.restTime.millis)
        }
    }

}

interface ConfigurationStore {
    fun getConfiguration(): Configuration
    fun putConfiguration(configuration: Configuration)
}
