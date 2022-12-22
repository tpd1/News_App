package com.example.newsapp.model

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapp.Constants
import com.example.newsapp.data.remote.RemoteNewsSource
import kotlinx.coroutines.launch


//Inject repository into viewModel

class ArticleViewModel (
    private val remoteNewsSource: RemoteNewsSource
) : ViewModel() {

    // Holds the responses from our API call. Exposed to NewsFragment so tabs can be created.
    //var newsLiveData = MutableLiveData<APIResponse>()

    private val currentCategory = MutableLiveData(DEFAULT)

    val newsLiveData = currentCategory.switchMap {
        remoteNewsSource.getPagingNews(it).cachedIn(viewModelScope)
    }

    fun getCategoryNews(category: String) {
        currentCategory.value = category
    }

//    fun getLatestNews() = viewModelScope.launch {
//        newsLiveData.value = remoteNewsSource.getLatestNews()
//    }

//    fun getCategoryNews(topic: String)  = viewModelScope.launch {
//        newsLiveData.value = remoteNewsSource.getCategoryNews(topic)
//    }

    companion object {
        private const val DEFAULT = Constants.TOP
    }

}