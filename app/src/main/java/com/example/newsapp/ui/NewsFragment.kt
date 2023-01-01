package com.example.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.Constants
import com.example.newsapp.Constants.Companion.SEARCH_TERM
import com.example.newsapp.model.NewsAdapter
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.model.NewsViewModel
import com.example.newsapp.model.NewsArticle
import com.example.newsapp.model.SettingsViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab

/**
 * Provides functionality to the news feed fragment which displays the RecyclerView list of articles.
 */
class NewsFragment : Fragment(R.layout.fragment_news), NewsAdapter.OnArticleClickListener {
    private lateinit var newsfeedBinding: FragmentNewsBinding
    private val adapter = NewsAdapter(this) // RecyclerView adapter
    private lateinit var newsViewModel: NewsViewModel//Uses delegate class viewModels which preserves across UI configuration changes.
    private lateinit var tabLayout: TabLayout // fetch tab layout XML object
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fetch view model instance from Main Activity as we need it in other fragments.
        newsViewModel = (activity as MainActivity).newsViewModel

        // Fetch the settings view model and observe the "Filter articles with no image URL" switch
        settingsViewModel = (activity as MainActivity).settingsViewModel
        settingsViewModel.filterImages.observe(viewLifecycleOwner) {
            newsViewModel.setFilterResults(it)
        }

        // Set up view/data binding
        newsfeedBinding = FragmentNewsBinding.bind(view)
        newsfeedBinding.apply {
            newsRecyclerView.adapter = adapter
            newsRecyclerView.setHasFixedSize(true)
            newsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            lifecycleOwner = this@NewsFragment
        }

        // Define the tab layout and set it up using helper function
        tabLayout = newsfeedBinding.tabLayout
        setupToolbar()

        // Observe changes to the article livedata list and update recyclerView
        newsViewModel.newsLiveData.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        // Set up listeners for tabs
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: Tab?) {
                if (tab != null) {
                    when (val topic = tab.text.toString().lowercase()) {
                        // Changes the live data category which triggers an API call
                        Constants.TRENDING -> newsViewModel.getCategoryNews(Constants.TOP)
                        else -> newsViewModel.getCategoryNews(topic)
                    }
                    newsViewModel.newsLiveData.observe(viewLifecycleOwner) {
                        adapter.submitData(viewLifecycleOwner.lifecycle, it)
                    }
                }
            }

            override fun onTabUnselected(tab: Tab?) {} // Not needed
            override fun onTabReselected(tab: Tab?) {} // Not needed
        })

        // Set on click listener for search button
        newsfeedBinding.searchButton.setOnClickListener {
            val query = newsfeedBinding.searchQueryText.text.toString()
            if (query.isNotBlank()) {
                newsViewModel.getSearchNews(query)
                // Observe the returned liveData and use if not null.
                newsViewModel.queryLiveData.observe(viewLifecycleOwner) {
                    if (it != null) {
                        adapter.submitData(viewLifecycleOwner.lifecycle, it)
                    } else Snackbar.make(
                        newsfeedBinding.root,
                        SEARCH_TERM,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            } else {
                Snackbar.make(
                    newsfeedBinding.root,
                    SEARCH_TERM,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            // Reset the search box.
            newsfeedBinding.searchQueryText.setText("")
        }
    }

    /**
     *  helper function to create a single tab in the tab layout, capitalising the tab name.
     *  @param topic The name of the tab to be created.
     */
    private fun createTab(topic: String) {
        val tab = tabLayout.newTab()
            .setText(topic.replaceFirstChar { it.uppercase() })
        tabLayout.addTab(tab)
    }

    /**
     * Allows the user to navigate to an article when clicked.
     * @param article the article that has been clicked
     */
    override fun onArticleClick(article: NewsArticle) {
        val action = NewsFragmentDirections.actionNewsFragmentToArticleFragment(article)
        findNavController().navigate(action)
    }


    /**
     * Sets up the tab layout based on the user-selected topics.
     */
    private fun setupToolbar() {
        val settingsViewModel =
            (activity as MainActivity).settingsViewModel // get topic View model for data

        tabLayout.removeAllTabs() // Start by removing all tabs from layout
        createTab(Constants.TRENDING) // Always have a trending tab.

        // For each topic category observe data changes and create a tab according to toggle position.
        // If the saved value is true then create a tab.
        settingsViewModel.businessEnabled.observe(viewLifecycleOwner) {
            if (it) createTab(Constants.BUSINESS)
        }

        settingsViewModel.entertainmentEnabled.observe(viewLifecycleOwner) {
            if (it) createTab(Constants.ENTERTAINMENT)
        }

        settingsViewModel.environmentEnabled.observe(viewLifecycleOwner) {
            if (it) createTab(Constants.ENVIRONMENT)
        }

        settingsViewModel.foodEnabled.observe(viewLifecycleOwner) {
            if (it) createTab(Constants.FOOD)
        }

        settingsViewModel.healthEnabled.observe(viewLifecycleOwner) {
            if (it) createTab(Constants.HEALTH)
        }

        settingsViewModel.politicsEnabled.observe(viewLifecycleOwner) {
            if (it) createTab(Constants.POLITICS)
        }

        settingsViewModel.scienceEnabled.observe(viewLifecycleOwner) {
            if (it) createTab(Constants.SCIENCE)
        }

        settingsViewModel.sportsEnabled.observe(viewLifecycleOwner) {
            if (it) createTab(Constants.SPORTS)
        }

        settingsViewModel.technologyEnabled.observe(viewLifecycleOwner) {
            if (it) createTab(Constants.TECHNOLOGY)
        }
    }

}