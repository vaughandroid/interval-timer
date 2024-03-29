package me.vaughandroid.intervaltimer.configuration.data

import me.vaughandroid.intervaltimer.configuration.domain.Configuration

class FakeConfigurationStore(
    var storedConfiguration: Configuration = Configuration()
) : ConfigurationStore {
    override fun getConfiguration(): Configuration = storedConfiguration

    override fun putConfiguration(configuration: Configuration) {
        storedConfiguration = configuration
    }
}