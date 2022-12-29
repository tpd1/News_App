package com.example.newsapp.weather

import com.example.newsapp.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * formulates a request for data from the WeatherAPI.
 * @property weatherAPIHandler Instance of the API interface used by Retrofit to fetch data
 */
class RemoteWeatherSource(private val weatherAPIHandler: WeatherApiService) {

    /**
     * Requests data from the Weather api for a particular location.
     * @param location The location search term.
     * @return The response from the weather API.
     */
    suspend fun getForecast(location: String): Response<WeatherApiResponse> {
        return weatherAPIHandler.getWeatherForecast(location)
    }
}

/**
 * Controls the request to the Weather API using Retrofit.
 */
interface WeatherApiService {

    /**
     * Retrofit query to fetch the weather at a user-entered location
     * @param location The location search term.
     * @param apiKey The API key.
     * @return The response from the Weather API.
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
