package com.example.newsapp.weather

import com.example.newsapp.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

class RemoteWeatherSource(private val weatherAPIHandler: WeatherApiService) {

    suspend fun getForecast(location: String): Response<WeatherApiResponse> {
        return weatherAPIHandler.getWeatherForecast(location)
    }
}

/**
 * Controls the request
 */
interface WeatherApiService {

    /**
     * Retrofit query to fetch the weather at a user-entered location
     */
    @GET("forecast.json?days=5&aqi=no&alerts=no")
    suspend fun getWeatherForecast(
        @Query("q") location: String,
        @Query("key") apiKey: String = Constants.WEATHER_API_KEY
    ): Response<WeatherApiResponse>
}

/**
 * Provides a wrapper for the API response to easily catch errors when the user
 * is searching for locations. Provides a generic template for error handling.
 *
 * This class was adapted from a tutorial by Stefan Jovanovic.
 * https://github.com/stevdza-san/Foody/
 */
sealed class ApiResponseStatus<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T): ApiResponseStatus<T>(data)
    class Error<T>(message: String?, data: T? = null): ApiResponseStatus<T>(data, message)
}
