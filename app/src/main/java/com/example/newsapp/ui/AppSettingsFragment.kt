package com.example.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentAppSettingsBinding
import com.example.newsapp.model.SettingsViewModel
import com.google.firebase.auth.FirebaseAuth

/**
 * Class associated with the App Settings Fragment. Provides functionality to the UI elements.
 */
class AppSettingsFragment : Fragment(R.layout.fragment_app_settings) {
    private lateinit var appSettingsBinding: FragmentAppSettingsBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentAppSettingsBinding.inflate(inflater, container, false)
        appSettingsBinding = binding
        firebaseAuth = FirebaseAuth.getInstance()


        // Get reference to settings view model for stored preferences.
        settingsViewModel = (activity as MainActivity).settingsViewModel

        // Observe the 'Filter articles with no images' toggle.
        settingsViewModel.filterImages.observe(viewLifecycleOwner) {
            appSettingsBinding.filterResultsSwitch.isChecked = it
        }

        // Apply the changes to the filter articles button to the newsViewModel.
        // This is so the paging adapter can filter the results in the Data layer.
        val newsViewModel = (activity as MainActivity).newsViewModel
        appSettingsBinding.filterResultsSwitch.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.setFilterImages(isChecked)
            newsViewModel.setFilterResults(isChecked)
        }

        return binding.root
    }

}