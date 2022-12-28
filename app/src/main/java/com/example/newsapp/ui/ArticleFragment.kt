package com.example.newsapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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
import com.example.newsapp.Constants.Companion.BOOKMARK_ALREADY_SAVED
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
                if (isAlreadySaved(args.newsArticle.link)) {
                    menu.findItem(R.id.bookmark).setIcon(R.drawable.bookmark_added)
                }
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
        val articleEntity = SavedArticleEntity(0, args.newsArticle)
        // Insert into database using the ViewModel if not already there.
        val output: String = if (!isAlreadySaved(articleEntity.article.link)) {
            bookmarksViewModel.insertArticle(articleEntity)
            menuItem.setIcon(R.drawable.bookmark_added)
            BOOKMARK_ADDED
        } else {
            BOOKMARK_ALREADY_SAVED
        }
        Snackbar.make(articleBinding.articleLayout, output, Snackbar.LENGTH_SHORT).show()

    }

    /**
     * Checks the database to see if the article is arleady saved based on a matching url.
     * 'any' function returns true if any element matches, false otherwise.
     * @param url = The string URL to be checked against in the database
     */
    private fun isAlreadySaved(url: String) : Boolean {
        var found = false
        bookmarksViewModel.articles.observe(viewLifecycleOwner) {
            for (entity in it) {
                if (entity.article.link == url) {
                    found = true
                }
            }
        }
        return found
    }

    /**
     * Processes the 'share button' click event.
     */
    private fun processShareClick() {
        val msg = args.newsArticle.link
        val intent = Intent().apply {
            type = "text/plain"
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, msg)
        }

        val shareIntent = Intent.createChooser(intent, null)
        startActivity(shareIntent)
    }

}






