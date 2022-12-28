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
    private val currentCategory = MutableLiveData(DEFAULT)

    val newsLiveData = currentCategory.switchMap {
        remoteNewsSource.getPagingNews(it).cachedIn(viewModelScope)
    }

    fun setFilterResults(isFiltered: Boolean) {
        remoteNewsSource.filterResults = isFiltered
    }

    fun getCategoryNews(category: String) {
        currentCategory.value = category
    }

    companion object {
        private const val DEFAULT = Constants.TOP
    }

}