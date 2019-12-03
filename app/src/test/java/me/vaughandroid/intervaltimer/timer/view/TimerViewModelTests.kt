package me.vaughandroid.intervaltimer.timer.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import me.vaughandroid.intervaltimer.configuration.data.FakeConfigurationStore
import me.vaughandroid.intervaltimer.configuration.domain.Configuration
import me.vaughandroid.intervaltimer.time.SystemTimeProvider
import me.vaughandroid.intervaltimer.time.minutes
import me.vaughandroid.intervaltimer.time.seconds
import me.vaughandroid.intervaltimer.timer.domain.TimerModel
import org.junit.Rule
import org.junit.Test

class TimerViewModelTests {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `view data is emitted when registering for updates`() {
        // Given
        val viewModel = createViewModel()
        val spyViewDataObserver = SpyViewDataObserver()

        // When
        viewModel.viewDataLiveData.observeForever(spyViewDataObserver)

        // Then
        assertThat(spyViewDataObserver.receivedViewData).hasSize(1)
    }

    @Test
    fun `model is correctly transformed into view data`() {
        // Given
        val configuration = Configuration(
            sets = 7,
            workTime = 11.seconds,
            restTime = 1.minutes + 13.seconds
        )
        val viewModel = createViewModel(configuration)
        val spyViewDataObserver = SpyViewDataObserver()

        // When
        viewModel.viewDataLiveData.observeForever(spyViewDataObserver)

        // Then
        val expectedViewData = TimerViewData(
            setsText = "7",
            workTimeText = "0:11",
            restTimeText = "1:13"
        )
        assertThat(spyViewDataObserver.receivedViewData.first()).isEqualTo(expectedViewData)
    }

    private fun createViewModel(configuration: Configuration = Configuration()): TimerViewModel {
        val timerModel = TimerModel(FakeConfigurationStore(configuration), SystemTimeProvider())
        return TimerViewModel(timerModel)
    }

}

private class SpyViewDataObserver : Observer<TimerViewData> {

    val receivedViewData = mutableListOf<TimerViewData>()

    override fun onChanged(viewData: TimerViewData) {
        receivedViewData += viewData
    }

}

