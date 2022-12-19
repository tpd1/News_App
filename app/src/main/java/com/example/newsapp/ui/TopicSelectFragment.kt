package com.example.newsapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.newsapp.Categories
import com.example.newsapp.R
import com.example.newsapp.data.dataStore
import com.example.newsapp.databinding.FragmentTopicSelectBinding
import com.example.newsapp.model.TopicViewModel


class TopicSelectFragment : Fragment(R.layout.fragment_topic_select) {
    private lateinit var topicBinding: FragmentTopicSelectBinding
    private lateinit var topicsViewModel: TopicViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topicBinding = FragmentTopicSelectBinding.bind(view)
        //topicBinding.lifecycleOwner = this

        val dataRepo = (activity as MainActivity).dataStore
        //topicsViewModel = TopicViewModel(dataRepo)


    }

//    private fun setButtons() {
//        topicBinding.sportsTopicText.
//    }

//    private fun getToggleState(name: String): Boolean? {
//
//        val name2 = Categories.valueOf(name)
//        return topicViewModel.topicMap[name2]
//    }


//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        newsfeedBinding = FragmentNewsBinding.bind(view)
//        newsfeedBinding.lifecycleOwner = this
//
//        newsfeedBinding.newsRecyclerView.adapter = adapter
//        newsfeedBinding.newsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//
//        // Fetch repository instance from container created in Main Activity. Use it to create viewModel
//        val repo = (activity as MainActivity).utilsContainer.newsRepo
//        newsViewModel = ArticleViewModel(repo)
//
//        getTopNews()
//
//    }


}