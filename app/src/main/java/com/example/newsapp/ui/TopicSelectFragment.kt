package com.example.newsapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentTopicSelectBinding
import com.example.newsapp.model.SettingsViewModel

/**
 * Provides functionality to the topic selection fragment UI. Allows the user to select
 * which topics they wish to appear in the tab layout in the news feed fragment.
 */
class TopicSelectFragment : Fragment(R.layout.fragment_topic_select) {
    private lateinit var topicBinding: FragmentTopicSelectBinding
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topicBinding = FragmentTopicSelectBinding.bind(view)
        setUpTopics()


    }

    /**
     * Helper function to set up topics based on the live data in the settings view model.
     */
    private fun setUpTopics() {
        settingsViewModel = (activity as MainActivity).settingsViewModel

        // Data is held as LiveData in the TopicViewModel, so we must set an observer first.
        // Using LiveData means it is lifecycle-aware and this fragment is just the UI component.
        settingsViewModel.businessEnabled.observe(viewLifecycleOwner) {
            topicBinding.businessTopicSwitch.isChecked = it
        }

        settingsViewModel.entertainmentEnabled.observe(viewLifecycleOwner) {
            topicBinding.entertainmentTopicSwitch.isChecked = it
        }

        settingsViewModel.environmentEnabled.observe(viewLifecycleOwner) {
            topicBinding.environmentTopicSwitch.isChecked = it
        }

        settingsViewModel.foodEnabled.observe(viewLifecycleOwner) {
            topicBinding.foodTopicSwitch.isChecked = it
        }

        settingsViewModel.healthEnabled.observe(viewLifecycleOwner) {
            topicBinding.healthTopicSwitch.isChecked = it
        }

        settingsViewModel.politicsEnabled.observe(viewLifecycleOwner) {
            topicBinding.politicsTopicSwitch.isChecked = it
        }

        settingsViewModel.scienceEnabled.observe(viewLifecycleOwner) {
            topicBinding.scienceTopicSwitch.isChecked = it
        }

        settingsViewModel.sportsEnabled.observe(viewLifecycleOwner) {
            topicBinding.sportsTopicSwitch.isChecked = it
        }

        settingsViewModel.technologyEnabled.observe(viewLifecycleOwner) {
            topicBinding.technologyTopicSwitch.isChecked = it
        }

        // Add listeners to each topic switch.
        topicBinding.businessTopicSwitch.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.setBusinessEnabled(isChecked)
        }

        topicBinding.entertainmentTopicSwitch.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.setEntertainmentEnabled(isChecked)
        }

        topicBinding.environmentTopicSwitch.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.setEnvironmentEnabled(isChecked)
        }

        topicBinding.foodTopicSwitch.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.setFoodEnabled(isChecked)
        }

        topicBinding.healthTopicSwitch.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.setHealthEnabled(isChecked)
        }

        topicBinding.politicsTopicSwitch.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.setPoliticsEnabled(isChecked)
        }

        topicBinding.scienceTopicSwitch.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.setScienceEnabled(isChecked)
        }

        topicBinding.sportsTopicSwitch.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.setSportsEnabled(isChecked)
        }

        topicBinding.technologyTopicSwitch.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.setTechnologyEnabled(isChecked)
        }
    }

}