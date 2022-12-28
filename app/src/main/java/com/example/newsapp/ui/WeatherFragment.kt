package com.example.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentWeatherBinding
import com.example.newsapp.model.SettingsViewModel
import com.example.newsapp.weather.ApiResponseStatus
import com.example.newsapp.weather.WeatherViewModel
import com.google.android.material.snackbar.Snackbar
import kotlin.math.roundToInt

class WeatherFragment : Fragment(R.layout.fragment_weather) {
    private lateinit var weatherViewModel: WeatherViewModel // For fetching API data
    private lateinit var settingsViewModel: SettingsViewModel // For fetching data store saved location
    private lateinit var weatherBinding: FragmentWeatherBinding
    private lateinit var currentLocation: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherBinding = FragmentWeatherBinding.bind(view)
        // Inflate the layout for this fragment


        // Create instance of view model using the pre-created Retrofit instance.
        val remoteWeatherSource = (activity as MainActivity).utilsContainer.remoteWeatherSource
        weatherViewModel = WeatherViewModel(remoteWeatherSource)



        initialiseWeather()
    }

    private fun initialiseWeather() {
        settingsViewModel = (activity as MainActivity).settingsViewModel

        // Data is held as LiveData in the TopicViewModel, so we must set an observer first.
        // Using LiveData means it is lifecycle-aware and this fragment is just the UI component.
        settingsViewModel.weatherLocation.observe(viewLifecycleOwner) {
            currentLocation = it
            //weatherViewModel.getWeatherForecast(it.toString())
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
            getWeather(location)
        }
    }

    /**
     * This function and the use of the APIResponseStatus class was designed after following
     * a tutorial by Stefan Jovanovic.
     * https://github.com/stevdza-san/Foody/
     */
    private fun getWeather(location: String) {
        weatherViewModel.getWeatherForecast(location)
        weatherViewModel.weatherData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponseStatus.Success -> {
                    val weatherData = response.data!!
                    settingsViewModel.setWeatherLocation(currentLocation)
                    weatherBinding.temperatureValue.text =
                        formatTemp(weatherData.currentWeather.temperature)
                    weatherBinding.feelsLikeValue.text =
                        formatTemp(weatherData.currentWeather.feelsLikeTemp)
                    weatherBinding.conditionText.text =
                        weatherData.currentWeather.condition.description
                    weatherBinding.lastUpdatedText.text = weatherData.currentWeather.time
                    weatherBinding.locationText.text = weatherData.location.name

                    val url = "https:${weatherData.currentWeather.condition.iconUrl}"
                    Glide.with(this)
                        .load(url)
                        .into(weatherBinding.weatherIcon)
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

    private fun formatTemp(temp: Float): String {
        val stringTemp = temp.roundToInt()
        return "$stringTemp \u2103"
    }
}