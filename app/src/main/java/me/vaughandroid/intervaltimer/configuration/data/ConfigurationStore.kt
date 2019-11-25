package me.vaughandroid.intervaltimer.configuration.data

import me.vaughandroid.intervaltimer.configuration.domain.Configuration

interface ConfigurationStore {
    fun getConfiguration(): Configuration
    fun putConfiguration(configuration: Configuration)
}