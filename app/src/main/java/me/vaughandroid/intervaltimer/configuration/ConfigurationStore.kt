package me.vaughandroid.intervaltimer.configuration

interface ConfigurationStore {
    fun getConfiguration(): Configuration
    fun putConfiguration(configuration: Configuration)
}