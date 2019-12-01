package me.vaughandroid.intervaltimer.timer.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
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
        Truth.assertThat(spyViewDataObserver.receivedViewData).hasSize(1)
    }

    private fun createViewModel(): TimerViewModel {
        return TimerViewModel()
    }

}

private class SpyViewDataObserver : Observer<TimerViewData> {

    val receivedViewData = mutableListOf<TimerViewData>()

    override fun onChanged(viewData: TimerViewData) {
        receivedViewData += viewData
    }

}

