package com.example.newsapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentWeatherBinding
import com.example.newsapp.model.SettingsViewModel
import com.example.newsapp.weather.WeatherViewModel
import com.google.android.material.snackbar.Snackbar

class WeatherFragment : Fragment(R.layout.fragment_weather) {
    private lateinit var weatherViewModel: WeatherViewModel // For fetching API data
    private lateinit var settingsViewModel: SettingsViewModel // For fetching data store saved location
    private lateinit var weatherBinding: FragmentWeatherBinding

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
            weatherBinding.locationText.text = it
            weatherViewModel.getWeatherForecast(it.toString())
        }




        weatherViewModel.weatherData.observe(viewLifecycleOwner) {
            weatherBinding.temperatureText.text = it.currentWeather.temperature.toString()
            weatherBinding.windText.text = it.currentWeather.windSpeed.toString()
            weatherBinding.conditionText.text = it.currentWeather.condition.description

            Glide.with(this)
                .load(it.currentWeather.condition.iconUrl)
                .error(R.drawable.settings_icon)
                .into(weatherBinding.weatherIcon)
        }

        // Add click listener to search button
        weatherBinding.searchWeatherButton.setOnClickListener {
            processSearchButton()
        }
    }

    private fun processSearchButton() {
        val location = weatherBinding.locationEditText.text.toString()

        if (location.length < 3) {
            Snackbar.make(weatherBinding.root, "Please enter the full location name", Snackbar.LENGTH_SHORT).show()
        } else {
            settingsViewModel.setWeatherLocation(location)
            try {
                weatherViewModel.getWeatherForecast(location)
            } catch (e: Exception) {
               Log.e("Location Error", "Api response error on search button clicked.")
            }
        }
    }
}