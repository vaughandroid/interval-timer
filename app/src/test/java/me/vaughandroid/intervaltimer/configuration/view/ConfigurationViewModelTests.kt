package me.vaughandroid.intervaltimer.configuration.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import me.vaughandroid.intervaltimer.configuration.domain.Configuration
import me.vaughandroid.intervaltimer.configuration.domain.ConfigurationModel
import me.vaughandroid.intervaltimer.time.minutes
import me.vaughandroid.intervaltimer.time.seconds
import org.junit.Rule
import org.junit.Test

class ConfigurationViewModelTests {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `view data is emitted when registering for updates`() {
        // Given
        val configuration = Configuration(sets = 7)
        val model = ConfigurationModel(configuration)
        val viewModel = ConfigurationViewModel(model)
        val spyViewDataObserver = SpyViewDataObserver()

        // When
        viewModel.viewDataLiveData.observeForever(spyViewDataObserver)

        // Then
        assertThat(spyViewDataObserver.receivedViewData).hasSize(1)
    }

    @Test
    fun `view data is emitted when the current configuration changes`() {
        // Given
        val updatedConfiguration = Configuration(sets = 11)
        val model = ConfigurationModel()
        val viewModel = ConfigurationViewModel(model)
        val spyViewDataObserver = SpyViewDataObserver()
        viewModel.viewDataLiveData.observeForever(spyViewDataObserver)

        // When
        model.updateConfiguration(updatedConfiguration)

        // Then
        assertThat(spyViewDataObserver.receivedViewData).hasSize(2)
    }

    @Test
    fun `configuration values are transformed into view data correctly`() {
        val configuration = Configuration(
            sets = 12,
            workTime = 2.minutes + 30.seconds,
            restTime = 10.minutes
        )
        val expectedViewData = ConfigurationViewData(
                setsText = "12",
                workTimeText = "2:30",
                restTimeText = "10:00"
            )
        val model = ConfigurationModel(configuration)
        val viewModel = ConfigurationViewModel(model)
        val spyViewDataObserver = SpyViewDataObserver()

        // When
        viewModel.viewDataLiveData.observeForever(spyViewDataObserver)

        // Then
        assertThat(spyViewDataObserver.receivedViewData[0]).isEqualTo(expectedViewData)
    }

    @Test
    fun `sets can be incremented`() {
        // Given
        val configuration = Configuration(sets = 10)
        val model = ConfigurationModel(configuration)
        val viewModel = ConfigurationViewModel(model)
        val spyViewDataObserver = SpyViewDataObserver()
        viewModel.viewDataLiveData.observeForever(spyViewDataObserver)

        // When
        viewModel.incrementSets()

        // Then
        assertThat(spyViewDataObserver.receivedViewData.last().setsText).isEqualTo("11")
    }

    @Test
    fun `sets can be decremented`() {
        // Given
        val configuration = Configuration(sets = 10)
        val model = ConfigurationModel(configuration)
        val viewModel = ConfigurationViewModel(model)
        val spyViewDataObserver = SpyViewDataObserver()
        viewModel.viewDataLiveData.observeForever(spyViewDataObserver)

        // When
        viewModel.decrementSets()

        // Then
        assertThat(spyViewDataObserver.receivedViewData.last().setsText).isEqualTo("9")
    }

    @Test
    fun `work time can be incremented`() {
        // Given
        val configuration = Configuration(workTime = 20.seconds)
        val model = ConfigurationModel(configuration)
        val viewModel = ConfigurationViewModel(model)
        val spyViewDataObserver = SpyViewDataObserver()
        viewModel.viewDataLiveData.observeForever(spyViewDataObserver)

        // When
        viewModel.incrementWorkTime()

        // Then
        assertThat(spyViewDataObserver.receivedViewData.last().workTimeText).isEqualTo("0:21")
    }

    @Test
    fun `work time can be decremented`() {
        // Given
        val configuration = Configuration(workTime = 20.seconds)
        val model = ConfigurationModel(configuration)
        val viewModel = ConfigurationViewModel(model)
        val spyViewDataObserver = SpyViewDataObserver()
        viewModel.viewDataLiveData.observeForever(spyViewDataObserver)

        // When
        viewModel.decrementWorkTime()

        // Then
        assertThat(spyViewDataObserver.receivedViewData.last().workTimeText).isEqualTo("0:19")
    }

    @Test
    fun `rest time can be incremented`() {
        // Given
        val configuration = Configuration(restTime = 30.seconds)
        val model = ConfigurationModel(configuration)
        val viewModel = ConfigurationViewModel(model)
        val spyViewDataObserver = SpyViewDataObserver()
        viewModel.viewDataLiveData.observeForever(spyViewDataObserver)

        // When
        viewModel.incrementRestTime()

        // Then
        assertThat(spyViewDataObserver.receivedViewData.last().restTimeText).isEqualTo("0:31")
    }

    @Test
    fun `rest time can be decremented`() {
        // Given
        val configuration = Configuration(restTime = 30.seconds)
        val model = ConfigurationModel(configuration)
        val viewModel = ConfigurationViewModel(model)
        val spyViewDataObserver = SpyViewDataObserver()
        viewModel.viewDataLiveData.observeForever(spyViewDataObserver)

        // When
        viewModel.decrementRestTime()

        // Then
        assertThat(spyViewDataObserver.receivedViewData.last().restTimeText).isEqualTo("0:29")
    }

}

class SpyViewDataObserver : Observer<ConfigurationViewData> {

    val receivedViewData = mutableListOf<ConfigurationViewData>()

    override fun onChanged(viewData: ConfigurationViewData) {
        receivedViewData += viewData
    }

}
