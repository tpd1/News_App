package com.example.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.newsapp.Constants.Companion.ALL
import com.example.newsapp.Constants.Companion.NONE
import com.example.newsapp.Constants.Companion.SELECTED
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentAppSettingsBinding
import com.example.newsapp.model.SettingsViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

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

        // Set toggle group status based on DataStore saved value
        settingsViewModel.notifications.observe(viewLifecycleOwner) { status ->
            when (status) {
                ALL -> appSettingsBinding.allTopicsButton.isChecked = true
                SELECTED -> appSettingsBinding.selectedTopicsButton.isChecked = true
                NONE -> appSettingsBinding.noTopicsButton.isChecked = true
            }

        }

        // Set up notification demo button
        val notificationController = (activity as MainActivity).notificationController
        appSettingsBinding.demoNotificationsButton.setOnClickListener {
            lifecycleScope.launch {
                notificationController.getArticle()
            }
        }

        // Add click listener to toggle group and update settings datastore.
        appSettingsBinding.toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.no_topics_button -> {
                        settingsViewModel.setNotifications(NONE)
                    }
                    R.id.selected_topics_button -> {
                        settingsViewModel.setNotifications(SELECTED)
                    }
                    R.id.all_topics_button -> {
                        settingsViewModel.setNotifications(ALL)
                    }
                }
            }
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