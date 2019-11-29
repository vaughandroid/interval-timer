package me.vaughandroid.intervaltimer

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import me.vaughandroid.intervaltimer.di.AppDependencies
import me.vaughandroid.intervaltimer.di.ViewModelFactory

@Suppress("unused")
class IntervalTimerApplication : Application() {

    @VisibleForTesting
    fun clearAppContainer() {
        viewModelFactory = ViewModelFactory(AppDependencies(this))
    }

    @VisibleForTesting
    fun setAppContainer(appDependencies: AppDependencies) {
        viewModelFactory = ViewModelFactory(appDependencies)
    }

    var viewModelFactory: ViewModelFactory = ViewModelFactory(AppDependencies(this))
        private set

    companion object {

        fun <T : ViewModel> getViewModel(fragment: Fragment, clazz: Class<T>): T {
            val app = fragment.requireContext().applicationContext as IntervalTimerApplication
            return ViewModelProviders.of(fragment, app.viewModelFactory).get(clazz)
        }
    }

}