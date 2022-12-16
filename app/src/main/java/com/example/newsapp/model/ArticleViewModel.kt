package com.example.newsapp.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.database.NewsArticleRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


//Inject repository into viewModel
@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val repo: NewsArticleRepo,
    application: Application
) : AndroidViewModel(application) {

    // Holds the responses from our API call. Private because the activity shouldn't change it.
    var newsLiveData = MutableLiveData<APIResponse>()

    fun getLatestNews() = viewModelScope.launch {
        newsLiveData.value = repo.remote.getLatestNews()
    }


}