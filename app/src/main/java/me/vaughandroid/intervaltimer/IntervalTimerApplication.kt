package me.vaughandroid.intervaltimer

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
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

    companion object {

        fun <T : ViewModel> getViewModel(fragment: Fragment, clazz: Class<T>): T {
            val app = fragment.requireContext().applicationContext as IntervalTimerApplication
            return ViewModelProviders.of(fragment, app.viewModelFactory).get(clazz)
        }
    }

}