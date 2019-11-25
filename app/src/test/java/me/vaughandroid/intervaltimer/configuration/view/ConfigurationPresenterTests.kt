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
        val configurationPresenter =
            ConfigurationPresenter()
        val spyViewDataListener =
            SpyViewDataListener()
        configurationPresenter.viewDataListener = spyViewDataListener

        // When
        configurationPresenter.onConfigurationChanged(configuration)

        // Then
        val expectedViewData =
            ConfigurationViewData(
                setsText = "12",
                workTimeText = "2:30",
                restTimeText = "10:00"
            )
        assertThat(spyViewDataListener.receivedViewData).isEqualTo(expectedViewData)
    }

}

class SpyViewDataListener : (ConfigurationViewData) -> Unit {

    var receivedViewData: ConfigurationViewData? = null

    override fun invoke(configurationViewData: ConfigurationViewData) {
        receivedViewData = configurationViewData
    }

}

