package me.vaughandroid.intervaltimer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import me.vaughandroid.intervaltimer.configuration.view.ConfigurationFragment
import me.vaughandroid.intervaltimer.timer.view.TimerFragment

class MainActivity : AppCompatActivity() {

    private var currentScreen: Screen? = null
    private var currentFragment: Fragment? = null

    private val navigationEventHandler = { screen: Screen -> goToScreen(screen) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goToScreen(Screen.CONFIGURATION)
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)

        when (fragment) {
            is ConfigurationFragment -> fragment.navigationEventHandler = navigationEventHandler
        }
    }

    private fun goToScreen(screen: Screen) {
        if (currentScreen != screen) {
            val newFragment = when (screen) {
                Screen.CONFIGURATION -> ConfigurationFragment()
                Screen.TIMER -> TimerFragment()
            }

            val transaction = supportFragmentManager.beginTransaction()
            currentFragment?.let { transaction.remove(it) }
            transaction
                .add(R.id.root, newFragment)
                .commit()

            currentScreen = screen
            currentFragment = newFragment
        }
    }

}
