package com.example.newsapp.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newsapp.Constants
import com.example.newsapp.data.remote.RemoteNewsSource

/**
 * ViewModel class for the news feed fragment. Provides an interface between the UI layer
 * and the remote data source.
 * @property remoteNewsSource The instance of a class that handles API calls.
 */
class NewsViewModel(
    private val remoteNewsSource: RemoteNewsSource
) : ViewModel() {

    // Holds the currently selected category as live data.
    private val currentCategory = MutableLiveData(Constants.TOP)

    // Holds the current search query as live data.
    private val currentQuery = MutableLiveData("")

    // Fetches paging news based on category when currentCategory is changed.
    // The use of a switchMap is credited to the tutorial by Florian Walther:
    // https://github.com/codinginflow/ImageSearchApp
    val newsLiveData = currentCategory.switchMap {
        remoteNewsSource.getPagingCategoryNews(it).cachedIn(viewModelScope)
    }

    // Fetches paging news based on search query when currentQuery is changed
    val queryLiveData = currentQuery.switchMap {
        if (!it.isNullOrBlank()) {
            remoteNewsSource.getPagingSearchNews(it).cachedIn(viewModelScope)
        } else {
            newsLiveData
        }
    }

    /**
     * Allows the user to hide news articles that have no imageURL
     * @param isFiltered true if news is to be filtered, false otherwise
     */
    fun setFilterResults(isFiltered: Boolean) {
        remoteNewsSource.filterResults = isFiltered
    }

    /**
     * Allows a user to select a category, changes the livedata variable.
     * @param category The selected news topic.
     */
    fun getCategoryNews(category: String) {
        currentCategory.value = category
    }

    /**
     * Allows the user to enter a search term. Changes the query livedata.
     * @param q The search term.
     */
    fun getSearchNews(q: String) {
        currentQuery.value = q
    }

}