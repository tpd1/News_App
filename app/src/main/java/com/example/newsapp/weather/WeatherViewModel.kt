package com.example.newsapp.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val remoteWeatherSource: RemoteWeatherSource
): ViewModel() {

    var weatherData = MutableLiveData<WeatherApiResponse>()

    fun getWeatherForecast(location: String) = viewModelScope.launch {
        weatherData.value = remoteWeatherSource.getForecast(location)
    }

}

