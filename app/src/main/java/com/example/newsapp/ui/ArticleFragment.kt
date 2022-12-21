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
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentArticleBinding

class ArticleFragment : Fragment(R.layout.fragment_article) {
    private val args by navArgs<ArticleFragmentArgs>()
    private lateinit var articleBinding: FragmentArticleBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articleBinding = FragmentArticleBinding.bind(view)

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
                        processBookMarkClick()
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

    private fun processBookMarkClick() {
        Log.i("Bookmark", "Bookmark Button clicked")
    }

    private fun processShareClick() {
        Log.i("Share", "Share button clicked")
    }





}






