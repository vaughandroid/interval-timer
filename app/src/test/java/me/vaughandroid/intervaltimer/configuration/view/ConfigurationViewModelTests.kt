package me.vaughandroid.intervaltimer.configuration.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import me.vaughandroid.intervaltimer.configuration.domain.Configuration
import me.vaughandroid.intervaltimer.configuration.domain.ConfigurationModel
import me.vaughandroid.intervaltimer.time.seconds
import org.junit.Rule
import org.junit.Test

class ConfigurationViewModelTests {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `view data is emitted when registering for updates`() {
        // Given
        val configuration = Configuration(
            sets = 12,
            workTime = 20.seconds,
            restTime = 10.seconds
        )
        val expectedViewData = ConfigurationViewData(
            setsText = "12",
            workTimeText = "0:20",
            restTimeText = "0:10"
        )
        val model = ConfigurationModel(configuration)
        val viewModel = ConfigurationViewModel(model)
        val spyViewDataObserver = SpyViewDataObserver()

        // When
        viewModel.viewDataLiveData.observeForever(spyViewDataObserver)

        // Then
        assertThat(spyViewDataObserver.receivedViewData).isEqualTo(expectedViewData)
    }

    @Test
    fun `view data is emitted when the current configuration changes`() {
        // Given
        val updatedConfiguration = Configuration(
            sets = 17,
            workTime = 19.seconds,
            restTime = 23.seconds
        )
        val expectedViewData = ConfigurationViewData(
            setsText = "17",
            workTimeText = "0:19",
            restTimeText = "0:23"
        )
        val model = ConfigurationModel()
        val viewModel = ConfigurationViewModel(model)
        val spyViewDataObserver = SpyViewDataObserver()
        viewModel.viewDataLiveData.observeForever(spyViewDataObserver)

        // When
        model.currentConfiguration = updatedConfiguration

        // Then
        assertThat(spyViewDataObserver.receivedViewData).isEqualTo(expectedViewData)
    }

}

class SpyViewDataObserver : Observer<ConfigurationViewData> {

    var receivedViewData: ConfigurationViewData? = null

    override fun onChanged(viewData: ConfigurationViewData) {
        receivedViewData = viewData
    }

}
