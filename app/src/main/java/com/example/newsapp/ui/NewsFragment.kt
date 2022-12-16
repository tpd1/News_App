package com.example.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.newsapp.model.NewsAdapter
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.model.NewsArticle
import com.example.newsapp.model.ArticleViewModel

class NewsFragment : Fragment(R.layout.fragment_news) {
    private lateinit var newsViewModel : ArticleViewModel
    private lateinit var newsfeedBinding: FragmentNewsBinding
    private val adapter = NewsAdapter()

    // View is passed from Fragment constructor because we defined it there.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsfeedBinding = FragmentNewsBinding.bind(view)
        newsfeedBinding.lifecycleOwner = this

//        newsViewModel = ViewModelProvider(requireActivity())[ArticleViewModel::class.java]
//        newsfeedBinding.newsRecyclerView.adapter = adapter
//        newsfeedBinding.newsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//
//
//        getTopNews()

        }


    private fun getTopNews() {
        newsViewModel.getLatestNews()
        newsViewModel.newsLiveData.observe(viewLifecycleOwner) { response ->
            response.results.let { adapter.setList(it as MutableList<NewsArticle>) }
        }
    }


}