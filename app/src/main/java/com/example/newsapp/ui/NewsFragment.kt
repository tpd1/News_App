package com.example.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.model.NewsAdapter
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.model.ArticleViewModel
import com.example.newsapp.model.NewsArticle

class NewsFragment : Fragment(R.layout.fragment_news) {
    private lateinit var newsfeedBinding: FragmentNewsBinding
    private val adapter = NewsAdapter() // RecyclerView adapter
    private lateinit var newsViewModel: ArticleViewModel//Uses delegate class viewModels which preserves across UI configuration changes.

    // View is passed from Fragment constructor because we defined it there.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsfeedBinding = FragmentNewsBinding.bind(view)
        newsfeedBinding.lifecycleOwner = this

        newsfeedBinding.newsRecyclerView.adapter = adapter
        newsfeedBinding.newsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Fetch repository instance from container created in Main Activity. Use it to create viewModel
        val repo = (activity as MainActivity).utilsContainer.newsRepo
        newsViewModel = ArticleViewModel(repo)

        //getTopNews()

        }


    // Helper function
    private fun getTopNews() {
        newsViewModel.getLatestNews()
        newsViewModel.newsLiveData.observe(viewLifecycleOwner) { response ->
            response.results.let { adapter.setList(it as MutableList<NewsArticle>) }
        }
    }


}