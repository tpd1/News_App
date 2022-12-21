package com.example.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.data.local.SavedArticleEntity
import com.example.newsapp.databinding.FragmentBookmarksBinding
import com.example.newsapp.model.*

class BookmarksFragment : Fragment(R.layout.fragment_bookmarks), BookmarksAdapter.OnSavedArticleClickListener {
    private val bookmarkAdapter = BookmarksAdapter(this)
    private lateinit var bookmarksBinding: FragmentBookmarksBinding
    private lateinit var bookmarksViewModel: BookmarksViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookmarksViewModel = (activity as MainActivity).bookmarksViewModel
        bookmarksBinding = FragmentBookmarksBinding.bind(view)
        bookmarksBinding.bookmarksRecyclerView.adapter = bookmarkAdapter
        bookmarksBinding.bookmarksRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        bookmarksViewModel.articles.observe(viewLifecycleOwner) { response ->
            bookmarkAdapter.setList(response as MutableList<SavedArticleEntity>)
        }
    }


    override fun onSavedArticleClick(article: NewsArticle) {
        val action = BookmarksFragmentDirections.actionBookmarksFragmentToArticleFragment(article)
        findNavController().navigate(action)
    }
}


