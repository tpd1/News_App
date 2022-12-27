package com.example.newsapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.newsapp.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Create a DataStore instance. Context passed from MainActivity
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.PREF_DATASTORE_NAME)

class DataStoreRepo(context: Context) {
    private val dataStore = context.dataStore // This is the only way i could make it work?

    // Set up keys that will be used in the DataStore. These match the topics available for selection.
    object TopicKeys {
        val business = booleanPreferencesKey(Constants.BUSINESS)
        val entertainment = booleanPreferencesKey(Constants.ENTERTAINMENT)
        val environment = booleanPreferencesKey(Constants.ENVIRONMENT)
        val food = booleanPreferencesKey(Constants.FOOD)
        val health = booleanPreferencesKey(Constants.HEALTH)
        val politics = booleanPreferencesKey(Constants.POLITICS)
        val science = booleanPreferencesKey(Constants.SCIENCE)
        val sports = booleanPreferencesKey(Constants.SPORTS)
        val technology = booleanPreferencesKey(Constants.TECHNOLOGY)
    }

    object AppSettings {
        val weatherLocation = stringPreferencesKey(Constants.WEATHER_LOCATION)
    }

    val weatherLocationFlow: Flow<String> = dataStore.data.map { i ->
        i[AppSettings.weatherLocation] ?: "London"
    }

    //For each topic create a variable to store as a Flow
    val businessEnabled: Flow<Boolean> = dataStore.data.map { i ->
        i[TopicKeys.business] ?: false
    }

    val entertainmentEnabled: Flow<Boolean> = dataStore.data.map { i ->
        i[TopicKeys.entertainment] ?: false
    }

    val environmentEnabled: Flow<Boolean> = dataStore.data.map { i ->
        i[TopicKeys.environment] ?: false
    }

    val foodEnabled: Flow<Boolean> = dataStore.data.map { i ->
        i[TopicKeys.food] ?: false
    }

    val healthEnabled: Flow<Boolean> = dataStore.data.map { i ->
        i[TopicKeys.health] ?: false
    }

    val politicsEnabled: Flow<Boolean> = dataStore.data.map { i ->
        i[TopicKeys.politics] ?: false
    }

    val scienceEnabled: Flow<Boolean> = dataStore.data.map { i ->
        i[TopicKeys.science] ?: false
    }

    val sportsEnabled: Flow<Boolean> = dataStore.data.map { i ->
        i[TopicKeys.sports] ?: false
    }

    val technologyEnabled: Flow<Boolean> = dataStore.data.map { i ->
        i[TopicKeys.technology] ?: false
    }

    // Called when changing a datastore preference (as the user toggles a switch).
    suspend fun setBusinessEnabled(enabled: Boolean) {
        dataStore.edit { pref -> pref[TopicKeys.business] = enabled }
    }

    suspend fun setEntertainmentEnabled(enabled: Boolean) {
        dataStore.edit { pref -> pref[TopicKeys.entertainment] = enabled }
    }

    suspend fun setEnvironmentEnabled(enabled: Boolean) {
        dataStore.edit { pref -> pref[TopicKeys.environment] = enabled }
    }

    suspend fun setFoodEnabled(enabled: Boolean) {
        dataStore.edit { pref -> pref[TopicKeys.food] = enabled }
    }

    suspend fun setHealthEnabled(enabled: Boolean) {
        dataStore.edit { pref -> pref[TopicKeys.health] = enabled }
    }

    suspend fun setPoliticsEnabled(enabled: Boolean) {
        dataStore.edit { pref -> pref[TopicKeys.politics] = enabled }
    }

    suspend fun setScienceEnabled(enabled: Boolean) {
        dataStore.edit { pref -> pref[TopicKeys.science] = enabled }
    }

    suspend fun setSportsEnabled(enabled: Boolean) {
        dataStore.edit { pref -> pref[TopicKeys.sports] = enabled }
    }

    suspend fun setTechnologyEnabled(enabled: Boolean) {
        dataStore.edit { pref -> pref[TopicKeys.technology] = enabled }
    }

    suspend fun setWeatherLocation(location: String) {
        dataStore.edit {pref -> pref[AppSettings.weatherLocation] = location}
    }

}

