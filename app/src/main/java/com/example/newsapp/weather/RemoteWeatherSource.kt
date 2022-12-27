package com.example.newsapp.weather

import com.example.newsapp.Constants
import retrofit2.http.GET
import retrofit2.http.Query

class RemoteWeatherSource(private val weatherAPIHandler: WeatherApiService) {

    suspend fun getForecast(location: String) : WeatherApiResponse =
        weatherAPIHandler.getWeatherForecast(location)
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
    ): WeatherApiResponse
}
