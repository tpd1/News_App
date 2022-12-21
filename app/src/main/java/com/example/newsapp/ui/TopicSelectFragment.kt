package com.example.newsapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentTopicSelectBinding
import com.example.newsapp.model.TopicViewModel


class TopicSelectFragment : Fragment(R.layout.fragment_topic_select) {
    private lateinit var topicBinding: FragmentTopicSelectBinding
    private lateinit var topicsViewModel: TopicViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topicBinding = FragmentTopicSelectBinding.bind(view)

        setUpTopics()


    }

    private fun setUpTopics() {
        topicsViewModel = (activity as MainActivity).topicsViewModel

        // Data is held as LiveData in the TopicViewModel, so we must set an observer first.
        // Using LiveData means it is lifecycle-aware and this fragment is just the UI component.
        topicsViewModel.businessEnabled.observe(viewLifecycleOwner) {
            topicBinding.businessTopicSwitch.isChecked = it
        }

        topicsViewModel.entertainmentEnabled.observe(viewLifecycleOwner) {
            topicBinding.entertainmentTopicSwitch.isChecked = it
        }

        topicsViewModel.environmentEnabled.observe(viewLifecycleOwner) {
            topicBinding.environmentTopicSwitch.isChecked = it
        }

        topicsViewModel.foodEnabled.observe(viewLifecycleOwner) {
            topicBinding.foodTopicSwitch.isChecked = it
        }

        topicsViewModel.healthEnabled.observe(viewLifecycleOwner) {
            topicBinding.healthTopicSwitch.isChecked = it
        }

        topicsViewModel.politicsEnabled.observe(viewLifecycleOwner) {
            topicBinding.politicsTopicSwitch.isChecked = it
        }

        topicsViewModel.scienceEnabled.observe(viewLifecycleOwner) {
            topicBinding.scienceTopicSwitch.isChecked = it
        }

        topicsViewModel.sportsEnabled.observe(viewLifecycleOwner) {
            topicBinding.sportsTopicSwitch.isChecked = it
        }

        topicsViewModel.technologyEnabled.observe(viewLifecycleOwner) {
            topicBinding.technologyTopicSwitch.isChecked = it
        }

        // Add listeners to each topic switch (might be an easier way to do this?)
        topicBinding.businessTopicSwitch.setOnCheckedChangeListener { _, isChecked ->
            topicsViewModel.setBusinessEnabled(isChecked)
        }

        topicBinding.entertainmentTopicSwitch.setOnCheckedChangeListener { _, isChecked ->
            topicsViewModel.setEntertainmentEnabled(isChecked)
        }

        topicBinding.environmentTopicSwitch.setOnCheckedChangeListener { _, isChecked ->
            topicsViewModel.setEnvironmentEnabled(isChecked)
        }

        topicBinding.foodTopicSwitch.setOnCheckedChangeListener { _, isChecked ->
            topicsViewModel.setFoodEnabled(isChecked)
        }

        topicBinding.healthTopicSwitch.setOnCheckedChangeListener { _, isChecked ->
            topicsViewModel.setHealthEnabled(isChecked)
        }

        topicBinding.politicsTopicSwitch.setOnCheckedChangeListener { _, isChecked ->
            topicsViewModel.setPoliticsEnabled(isChecked)
        }

        topicBinding.scienceTopicSwitch.setOnCheckedChangeListener { _, isChecked ->
            topicsViewModel.setScienceEnabled(isChecked)
        }

        topicBinding.sportsTopicSwitch.setOnCheckedChangeListener { _, isChecked ->
            topicsViewModel.setSportsEnabled(isChecked)
        }

        topicBinding.technologyTopicSwitch.setOnCheckedChangeListener { _, isChecked ->
            topicsViewModel.setTechnologyEnabled(isChecked)
        }
    }

}