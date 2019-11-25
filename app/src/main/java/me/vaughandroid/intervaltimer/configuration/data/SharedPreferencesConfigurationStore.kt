package me.vaughandroid.intervaltimer.configuration.data

import android.content.Context
import androidx.core.content.edit
import me.vaughandroid.intervaltimer.configuration.domain.Configuration
import me.vaughandroid.intervaltimer.time.Duration

class SharedPreferencesConfigurationStore(
    context: Context
) : ConfigurationStore {

    companion object {
        internal const val SHARED_PREFS_NAME = "configuration_store"

        private const val KEY_SETS = "sets"
        private const val KEY_WORK_TIME = "work_time"
        private const val KEY_REST_TIME = "rest_time"

    }

    private val sharedPreferences = context.getSharedPreferences(
        SHARED_PREFS_NAME,
        Context.MODE_PRIVATE
    )

    override fun getConfiguration(): Configuration {
        return if (sharedPreferences.contains(KEY_SETS)) {
            Configuration(
                sets = sharedPreferences.getInt(
                    KEY_SETS,
                    1
                ),
                workTime = Duration(
                    sharedPreferences.getInt(
                        KEY_WORK_TIME,
                        1000
                    )
                ),
                restTime = Duration(
                    sharedPreferences.getInt(
                        KEY_REST_TIME,
                        1000
                    )
                )
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