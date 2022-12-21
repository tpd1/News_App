package com.example.newsapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.webkit.WebViewClient
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import com.example.newsapp.Constants.Companion.BOOKMARK_ADDED
import com.example.newsapp.R
import com.example.newsapp.data.local.SavedArticleEntity
import com.example.newsapp.databinding.FragmentArticleBinding
import com.example.newsapp.model.BookmarksViewModel
import com.google.android.material.snackbar.Snackbar

class ArticleFragment : Fragment(R.layout.fragment_article) {
    private val args by navArgs<ArticleFragmentArgs>()
    private lateinit var articleBinding: FragmentArticleBinding
    private lateinit var bookmarksViewModel: BookmarksViewModel

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articleBinding = FragmentArticleBinding.bind(view)

        bookmarksViewModel = (activity as MainActivity).bookmarksViewModel

        // Latest method to control the menu bar after update:
        //https://developer.android.com/jetpack/androidx/releases/activity#1.4.0-alpha01
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            // Show the article-specific menu, which includes bookmark and share buttons.
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.toolbar_top_article, menu)
            }

            // Process button button clicks for share and bookmark menu options.
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.bookmark -> {
                        processBookmarkClick(menuItem)
                    }
                    R.id.share_button -> {
                        processShareClick()
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)


        // Load the webpage into the webview
        val url = args.newsArticle.link
        Log.i("url", "url: $url")
        articleBinding.articleWebView.webViewClient = WebViewClient()
        articleBinding.articleWebView.apply {
            settings.javaScriptEnabled = true
            loadUrl(url)
        }
    }

    /**
     * Processes the click event for the 'add to bookmarks' button
     * @param menuItem - Required in order to change the icon to indicate bookmark is added.
     */
    private fun processBookmarkClick(menuItem: MenuItem) {
        // Create a new database entity from the args passed to the activity fragment.
        val articleEntity = SavedArticleEntity(0, args.newsArticle)
        // Insert into database using the ViewModel
        bookmarksViewModel.insertArticle(articleEntity)
        // Change the icon and show Snackbar to indicate bookmark has been added.
        menuItem.setIcon(R.drawable.bookmark_added)
        Snackbar.make(articleBinding.articleLayout, BOOKMARK_ADDED, Snackbar.LENGTH_SHORT).show()
    }

    /**
     * Processes the 'share button' click event.
     */
    private fun processShareClick() {
        TODO("Implement social media sharing")
    }


}






