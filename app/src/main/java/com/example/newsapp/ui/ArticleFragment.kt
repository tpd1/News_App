package com.example.newsapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
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

        // Swap the top menu bar for the article-specific one.
        val mainBinding = (activity as MainActivity).mainBinding
        mainBinding.topNavBar.menu.clear()
        mainBinding.topNavBar.inflateMenu(R.menu.toolbar_top_article)

        // Load the webpage into the webview
        val url = args.newsArticle.link
        Log.i("url", "url: $url")
        articleBinding.articleWebView.webViewClient = WebViewClient()
        articleBinding.articleWebView.apply {
            settings.javaScriptEnabled = true
            loadUrl(url)
        }
    }

}






