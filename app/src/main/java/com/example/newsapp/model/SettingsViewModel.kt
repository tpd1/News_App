package com.example.newsapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.DataStoreRepo
import kotlinx.coroutines.launch

/**
 * Provides an interface between the DataStore layer and the UI. Used to store user
 * settings within the app.
 * @property dataRepo The DataStore repository instance used to store settings.
 */
class SettingsViewModel(private val dataRepo: DataStoreRepo) : ViewModel() {

    // Topic selection switches
    val businessEnabled: LiveData<Boolean> = dataRepo.businessEnabled.asLiveData()
    val entertainmentEnabled: LiveData<Boolean> = dataRepo.entertainmentEnabled.asLiveData()
    val environmentEnabled: LiveData<Boolean> = dataRepo.environmentEnabled.asLiveData()
    val foodEnabled: LiveData<Boolean> = dataRepo.foodEnabled.asLiveData()
    val healthEnabled: LiveData<Boolean> = dataRepo.healthEnabled.asLiveData()
    val politicsEnabled: LiveData<Boolean> = dataRepo.politicsEnabled.asLiveData()
    val scienceEnabled: LiveData<Boolean> = dataRepo.scienceEnabled.asLiveData()
    val sportsEnabled: LiveData<Boolean> = dataRepo.sportsEnabled.asLiveData()
    val technologyEnabled: LiveData<Boolean> = dataRepo.technologyEnabled.asLiveData()

    // Other app settings
    val weatherLocation: LiveData<String> = dataRepo.weatherLocationFlow.asLiveData()
    val filterImages: LiveData<Boolean> = dataRepo.filterNoImagesFlow.asLiveData()

    // Functions to change the values stored in the data repository.
    fun setFilterImages(enabled: Boolean) {
        viewModelScope.launch { dataRepo.setFilterImages(enabled) }
    }

    fun setWeatherLocation(location: String) {
        viewModelScope.launch { dataRepo.setWeatherLocation(location) }
    }


    fun setBusinessEnabled(value: Boolean) {
        viewModelScope.launch { dataRepo.setBusinessEnabled(value) }
    }

    fun setEntertainmentEnabled(value: Boolean) {
        viewModelScope.launch { dataRepo.setEntertainmentEnabled(value) }
    }

    fun setEnvironmentEnabled(value: Boolean) {
        viewModelScope.launch { dataRepo.setEnvironmentEnabled(value) }
    }

    fun setFoodEnabled(value: Boolean) {
        viewModelScope.launch { dataRepo.setFoodEnabled(value) }
    }

    fun setHealthEnabled(value: Boolean) {
        viewModelScope.launch { dataRepo.setHealthEnabled(value) }
    }

    fun setPoliticsEnabled(value: Boolean) {
        viewModelScope.launch { dataRepo.setPoliticsEnabled(value) }
    }

    fun setScienceEnabled(value: Boolean) {
        viewModelScope.launch { dataRepo.setScienceEnabled(value) }
    }

    fun setSportsEnabled(value: Boolean) {
        viewModelScope.launch { dataRepo.setSportsEnabled(value) }
    }

    fun setTechnologyEnabled(value: Boolean) {
        viewModelScope.launch { dataRepo.setTechnologyEnabled(value) }
    }
}