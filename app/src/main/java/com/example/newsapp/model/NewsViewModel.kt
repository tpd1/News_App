package com.example.newsapp.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newsapp.Constants
import com.example.newsapp.data.remote.RemoteNewsSource


class NewsViewModel (
    private val remoteNewsSource: RemoteNewsSource
) : ViewModel() {

    // Holds the responses from our API call. Exposed to NewsFragment so tabs can be created.
    private val currentCategory = MutableLiveData(DEFAULT_CATEGORY)
    private val currentQuery = MutableLiveData(DEFAULT_SEARCH)

    val newsLiveData = currentCategory.switchMap {
        remoteNewsSource.getPagingCategoryNews(it).cachedIn(viewModelScope)
    }

    val queryLiveData = currentQuery.switchMap {
        if (!it.isNullOrBlank()) {
            remoteNewsSource.getPagingSearchNews(it).cachedIn(viewModelScope)
        } else {
            newsLiveData
        }
    }


    fun setFilterResults(isFiltered: Boolean) {
        remoteNewsSource.filterResults = isFiltered
    }

    fun getCategoryNews(category: String) {
        currentCategory.value = category
    }

    fun getSearchNews(q: String) {
        currentQuery.value = q
    }

    companion object {
        private const val DEFAULT_CATEGORY = Constants.TOP
        private const val DEFAULT_SEARCH = ""
    }

}