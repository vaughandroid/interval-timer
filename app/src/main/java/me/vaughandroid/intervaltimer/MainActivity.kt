package me.vaughandroid.intervaltimer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import me.vaughandroid.intervaltimer.configuration.view.ConfigurationFragment
import me.vaughandroid.intervaltimer.timer.TimerFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onAttachFragment(fragment: androidx.fragment.app.Fragment) {
        super.onAttachFragment(fragment)

        when (fragment) {
            is ConfigurationFragment -> fragment.navigationEventHandler = navigationEventHandler
        }
    }

    private val navigationEventHandler = { navigationEvent : NavigationEvent ->
        when (navigationEvent) {
            NavigationEvent.TIMER -> goToTimer()
        }
    }

    private fun goToTimer() {
        supportFragmentManager.beginTransaction()
            .add(R.id.root, TimerFragment())
            .commit()
    }

}
