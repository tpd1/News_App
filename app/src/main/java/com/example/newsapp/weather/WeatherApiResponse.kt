package com.example.newsapp.weather

import com.google.gson.annotations.SerializedName

/**
 * Data class to model the top-level response from WeatherAPI.
 */
data class WeatherApiResponse(
    @SerializedName("current") val currentWeather: CurrentWeather,
    @SerializedName("location") val location: Location,
    @SerializedName("forecast") val forecast: WeeklyForecast
)

/**
 * Data class to model the location data returned from WeatherAPI.
 */
data class Location(
    @SerializedName("name") val name: String,
    @SerializedName("country") val country: String
)

/**
 * Models a weekly forecast, made up of multiple daily forecasts returned from API.
 */
data class WeeklyForecast(
    @SerializedName("forecastday") val dailyForecast: List<DailyForecast>
)

/**
 * Models a daily forecast returned from the API.
 */
data class DailyForecast(
    @SerializedName("date") val date: String,
    @SerializedName("day") val dayWeather: DayWeather
)

/**
 * Models the data for a daily forecast returned from the API.
 */
data class DayWeather(
    @SerializedName("maxtemp_c") val maxTemp: Float,
    @SerializedName("mintemp_c") val minTemp: Float,
    @SerializedName("daily_chance_of_rain") val rainChance: Int,
    @SerializedName("condition") val condition: WeatherCondition
)

/**
 * Models the weather conditions for a particular day, from the Weather API.
 */
data class WeatherCondition(
    @SerializedName("text") val description: String,
    @SerializedName("icon") val iconUrl: String,
    @SerializedName("code") val code: Int,
)

/**
 * Data class to model the current weather response from WeatherAPI.
 */
data class CurrentWeather(
    @SerializedName("temp_c") val temperature: Float,
    @SerializedName("wind_mph") val windSpeed: Float,
    @SerializedName("feelslike_c") val feelsLikeTemp: Float,
    @SerializedName("condition") val condition: WeatherCondition,
    @SerializedName("last_updated") val time: String
)


