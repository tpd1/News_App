package com.example.newsapp.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.ArticleRepository
import kotlinx.coroutines.launch


//Inject repository into viewModel

class ArticleViewModel (
    private val articleRepository: ArticleRepository
) : ViewModel() {

    // Holds the responses from our API call. Private because the activity shouldn't change it.
    var newsLiveData = MutableLiveData<APIResponse>()

    fun getLatestNews() = viewModelScope.launch {
        newsLiveData.value = articleRepository.getLatestNews()
    }

    fun getCategoryNews(topic: String)  = viewModelScope.launch {
        newsLiveData.value = articleRepository.getCategoryNews(topic)
    }


}