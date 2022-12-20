package com.example.newsapp.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.remote.RemoteNewsSource
import kotlinx.coroutines.launch


//Inject repository into viewModel

class ArticleViewModel (
    private val remoteNewsSource: RemoteNewsSource
) : ViewModel() {

    // Holds the responses from our API call. Exposed to NewsFragment so tabs can be created.
    var newsLiveData = MutableLiveData<APIResponse>()

    fun getLatestNews() = viewModelScope.launch {
        newsLiveData.value = remoteNewsSource.getLatestNews()
    }

    fun getCategoryNews(topic: String)  = viewModelScope.launch {
        newsLiveData.value = remoteNewsSource.getCategoryNews(topic)
    }

}