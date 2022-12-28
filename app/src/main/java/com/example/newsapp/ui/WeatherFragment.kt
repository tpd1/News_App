package com.example.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentWeatherBinding
import com.example.newsapp.model.SettingsViewModel
import com.example.newsapp.weather.ApiResponseStatus
import com.example.newsapp.weather.WeatherApiResponse
import com.example.newsapp.weather.WeatherViewModel
import com.google.android.material.snackbar.Snackbar
import kotlin.math.roundToInt

class WeatherFragment : Fragment(R.layout.fragment_weather) {
    private lateinit var weatherViewModel: WeatherViewModel // For fetching API data
    private lateinit var settingsViewModel: SettingsViewModel // For fetching data store saved location
    private lateinit var weatherBinding: FragmentWeatherBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherBinding = FragmentWeatherBinding.bind(view)

        // Create instance of view model using the pre-created Retrofit instance.
        val remoteWeatherSource = (activity as MainActivity).utilsContainer.remoteWeatherSource
        weatherViewModel = WeatherViewModel(remoteWeatherSource)
        settingsViewModel = (activity as MainActivity).settingsViewModel

        // Set observer for DataStore saved location - we only want to update this.
        settingsViewModel.weatherLocation.observe(viewLifecycleOwner) { location ->
            updateLocation(location)
        }

        // Add click listener to search button
        weatherBinding.searchWeatherButton.setOnClickListener {
            val location = weatherBinding.locationEditText.text.toString()
            processSearchButton(location)
        }
    }

    private fun processSearchButton(location: String) {
        if (location.length < 3) {
            Snackbar.make(
                weatherBinding.root,
                "Please enter the full location name",
                Snackbar.LENGTH_SHORT
            ).show()
        } else {
            settingsViewModel.setWeatherLocation(location)
        }
    }

    /**
     * This function and the use of the APIResponseStatus class was designed after following
     * a tutorial by Stefan Jovanovic.
     * https://github.com/stevdza-san/Foody/
     */
    private fun updateLocation(location: String) {
        weatherViewModel.getWeatherForecast(location)
        weatherViewModel.weatherData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponseStatus.Success -> {
                    val weatherData = response.data!!
                    updateUI(weatherData)
                }
                is ApiResponseStatus.Error -> {
                    Snackbar.make(
                        weatherBinding.root,
                        response.message.toString(),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    /**
     * Updates the UI elements
     */
    private fun updateUI(data: WeatherApiResponse) {
        weatherBinding.temperatureValue.text = formatTemp(data.currentWeather.temperature)
        weatherBinding.feelsLikeValue.text = formatTemp(data.currentWeather.feelsLikeTemp)
        weatherBinding.conditionText.text = data.currentWeather.condition.description
        weatherBinding.lastUpdatedText.text = formatDate(data.currentWeather.time)
        weatherBinding.locationText.text = data.location.name
        weatherBinding.countryText.text = data.location.country

        val url = "https:${data.currentWeather.condition.iconUrl}"
        Glide.with(this)
            .load(url)
            .into(weatherBinding.weatherIcon)
    }

    private fun formatTemp(temp: Float): String {
        val stringTemp = temp.roundToInt()
        return "$stringTemp \u2103"
    }

    private fun formatDate(date: String): String {
        val parts = date.split(" ")
        val time = parts[1]
        return "Last updated: $time"
    }
}