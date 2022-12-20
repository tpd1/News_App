package com.example.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.Constants
import com.example.newsapp.model.NewsAdapter
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.model.ArticleViewModel
import com.example.newsapp.model.NewsArticle
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab

class NewsFragment : Fragment(R.layout.fragment_news), NewsAdapter.OnArticleClickListener {
    private lateinit var newsfeedBinding: FragmentNewsBinding
    private val adapter = NewsAdapter(this) // RecyclerView adapter
    private lateinit var newsViewModel: ArticleViewModel//Uses delegate class viewModels which preserves across UI configuration changes.
    private lateinit var tabLayout: TabLayout // fetch tablayout XML object

    // View is passed from Fragment constructor because we defined it there.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fetch repository instance from container created in Main Activity. Use it to create viewModel
        val remoteNewsSource = (activity as MainActivity).utilsContainer.remoteDataSource
        newsViewModel = ArticleViewModel(remoteNewsSource)

        // Set up view/data binding
        newsfeedBinding = FragmentNewsBinding.bind(view)
        newsfeedBinding.lifecycleOwner = this
        newsfeedBinding.newsRecyclerView.adapter = adapter
        newsfeedBinding.newsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Define the tab layout and set it up using helper function
        tabLayout = newsfeedBinding.tabLayout
        setupToolbar()

        // Initially load the trending / latest news.
        newsViewModel.getLatestNews()
        newsViewModel.newsLiveData.observe(viewLifecycleOwner) { response ->
            response.results.let { adapter.setList(it as MutableList<NewsArticle>) }
        }

        // Set up listeners for tabs
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: Tab?) {
                if (tab != null) {
                    when (val topic = tab.text.toString().lowercase()) {
                        Constants.TRENDING -> newsViewModel.getLatestNews()
                        else -> newsViewModel.getCategoryNews(topic)
                    }
                    newsViewModel.newsLiveData.observe(viewLifecycleOwner) { response ->
                        response.results.let { adapter.setList(it as MutableList<NewsArticle>) }
                    }
                }
            }
            override fun onTabUnselected(tab: Tab?) {}
            override fun onTabReselected(tab: Tab?) {}
        })
    }

    // helper function to create a single tab in the tablayout, capitalising the tab name.
    private fun createTab(topic: String) {
        val tab = tabLayout.newTab()
            .setText(topic.replaceFirstChar { it.uppercase() })
        tabLayout.addTab(tab)
    }

    override fun onArticleClick(article: NewsArticle) {
        val action = NewsFragmentDirections.actionNewsFragmentToArticleFragment(article)
        findNavController().navigate(action)
    }

    // Choose tabs in toolbar based on user selected tabs. Uses a reference to the topicViewModel
    // to observe the liveData in the DataStore, and add tabs accordingly. Reacts to changes in topic selection.
    private fun setupToolbar() {
        val topicViewModel =
            (activity as MainActivity).topicsViewModel // get topicViewmodel for data

        tabLayout.removeAllTabs() // Start by removing all tabs from layout
        createTab(Constants.TRENDING)

        // For each topic category observe data changes and create a tab according to toggle position.
        // If the saved value is true then create a tab.
        topicViewModel.businessEnabled.observe(viewLifecycleOwner) {
            if (it) createTab(Constants.BUSINESS)
        }

        topicViewModel.entertainmentEnabled.observe(viewLifecycleOwner) {
            if (it) createTab(Constants.ENTERTAINMENT)
        }

        topicViewModel.environmentEnabled.observe(viewLifecycleOwner) {
            if (it) createTab(Constants.ENVIRONMENT)
        }

        topicViewModel.foodEnabled.observe(viewLifecycleOwner) {
            if (it) createTab(Constants.FOOD)
        }

        topicViewModel.healthEnabled.observe(viewLifecycleOwner) {
            if (it) createTab(Constants.HEALTH)
        }

        topicViewModel.politicsEnabled.observe(viewLifecycleOwner) {
            if (it) createTab(Constants.POLITICS)
        }

        topicViewModel.scienceEnabled.observe(viewLifecycleOwner) {
            if (it) createTab(Constants.SCIENCE)
        }

        topicViewModel.sportsEnabled.observe(viewLifecycleOwner) {
            if (it) createTab(Constants.SPORTS)
        }

        topicViewModel.technologyEnabled.observe(viewLifecycleOwner) {
            if (it) createTab(Constants.TECHNOLOGY)
        }
    }


}