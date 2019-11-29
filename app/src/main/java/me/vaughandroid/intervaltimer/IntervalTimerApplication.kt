package me.vaughandroid.intervaltimer

import android.app.Application
import androidx.annotation.VisibleForTesting
import me.vaughandroid.intervaltimer.di.AppContainer
import me.vaughandroid.intervaltimer.di.ViewModelFactory

@Suppress("unused")
class IntervalTimerApplication : Application() {

    @VisibleForTesting
    fun clearAppContainer() {
        viewModelFactory = ViewModelFactory(AppContainer(this))
    }

    @VisibleForTesting
    fun setAppContainer(appContainer: AppContainer) {
        viewModelFactory = ViewModelFactory(appContainer)
    }

    var viewModelFactory: ViewModelFactory = ViewModelFactory(AppContainer(this))
        private set

}