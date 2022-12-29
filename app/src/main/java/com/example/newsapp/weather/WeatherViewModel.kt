package com.example.newsapp.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Constants
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * View model for the weather bonus feature. provides a layer between the UI and data source in order
 * to store data in a lifecycle aware manner.
 * @property remoteWeatherSource The instance of the class that interacts with the Weather API.
 */
class WeatherViewModel(
    private val remoteWeatherSource: RemoteWeatherSource
) : ViewModel() {
    var weatherData = MutableLiveData<ApiResponseStatus<WeatherApiResponse>>()

    fun getWeatherForecast(location: String) = viewModelScope.launch {
        try {
            val data = remoteWeatherSource.getForecast(location)
            weatherData.value = handleAPIResponse(data)
        } catch (e: Exception) {
            weatherData.value = ApiResponseStatus.Error("Location Not Found")
        }
    }

    /**
     * Handles the response by the API, handling errors and returning the data wrapped in a
     * repose status class.
     *
     * This function and the idea of using a APIResponseStatus class was designed by following
     * a YouTube tutorial by Stefan Jovanovic. Adapted for use with the Weather API.
     * https://github.com/stevdza-san/Foody/
     */
    private fun handleAPIResponse(response: Response<WeatherApiResponse>): ApiResponseStatus<WeatherApiResponse> {
        return when {
            // List know / expected errors from WeatherAPI:
            response.code() == 400 -> {
                ApiResponseStatus.Error(Constants.ERROR_400)
            }
            (response.code() == 401 || response.code() == 403) -> {
                ApiResponseStatus.Error(Constants.ERROR_401)
            }
            // If response is 200-300 then return response body.
            response.isSuccessful -> {
                val responseBody = response.body()
                ApiResponseStatus.Success(responseBody!!)
            }
            else -> {
                // Un-categorised error.
                ApiResponseStatus.Error(Constants.UNKNOWN_ERROR)
            }
        }
    }
}

