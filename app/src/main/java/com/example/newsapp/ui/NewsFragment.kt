package com.example.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.NewsAdapter
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.model.NewsArticle
import com.example.newsapp.model.ArticleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment(R.layout.fragment_news) {
    private lateinit var newsViewModel : ArticleViewModel
    private lateinit var newsfeedBinding: FragmentNewsBinding
    private val adapter = NewsAdapter()

    // View is passed from Fragment constructor because we defined it there.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsfeedBinding = FragmentNewsBinding.bind(view)
        newsfeedBinding.lifecycleOwner = this

        newsViewModel = ViewModelProvider(requireActivity())[ArticleViewModel::class.java]
        newsfeedBinding.newsRecyclerView.adapter = adapter
        newsfeedBinding.newsRecyclerView.layoutManager = LinearLayoutManager(requireContext())


        getTopNews()

        }


    private fun getTopNews() {
        newsViewModel.getLatestNews()
        newsViewModel.newsLiveData.observe(viewLifecycleOwner) { response ->
            response.results.let { adapter.setData(it as MutableList<NewsArticle>) }
        }
    }


}