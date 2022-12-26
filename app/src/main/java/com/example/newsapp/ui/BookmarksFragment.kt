package com.example.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.Constants
import com.example.newsapp.R
import com.example.newsapp.data.local.SavedArticleEntity
import com.example.newsapp.databinding.FragmentBookmarksBinding
import com.example.newsapp.model.*
import com.google.android.material.snackbar.Snackbar

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
        // Recyclerview library to implement swipe to delete.
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // We aren't using this library to drag & drop, only swipe
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val article = bookmarkAdapter.getItem(viewHolder.absoluteAdapterPosition)
                bookmarksViewModel.deleteArticle(article)
                Snackbar.make(bookmarksBinding.root, "Bookmark deleted", Snackbar.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(bookmarksBinding.bookmarksRecyclerView)
    }


    override fun onSavedArticleClick(article: NewsArticle) {
        val action = BookmarksFragmentDirections.actionBookmarksFragmentToArticleFragment(article)
        findNavController().navigate(action)
    }
}


