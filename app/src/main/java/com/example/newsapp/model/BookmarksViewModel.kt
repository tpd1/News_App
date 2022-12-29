package com.example.newsapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.local.LocalNewsSource
import com.example.newsapp.data.local.SavedArticleEntity
import kotlinx.coroutines.launch

/**
 * View model to store articles fetched from the Room database.
 * Separates backend from UI to allow for a lifecycle-aware architecture.
 * Designed by following the 'Room with a View' Android code-labs tutorial:
 * https://developer.android.com/codelabs/android-room-with-a-view-kotlin#15
 * @property localNewsSource - Reference to the Room database source containing saved articles.
 */
class BookmarksViewModel (private val localNewsSource: LocalNewsSource) : ViewModel() {

    val articles: LiveData<List<SavedArticleEntity>> = localNewsSource.getAllArticles().asLiveData()

    /**
     * Inserts the selected article into the Room database.
     * @param articleEntity - The article to be inserted.
     */
    fun insertArticle(articleEntity: SavedArticleEntity) {
        viewModelScope.launch { localNewsSource.insertArticle(articleEntity) }
    }

    /**
     * Removes the selected article from the Room Database.
     * @param articleEntity - The article to be deleted.
     */
    fun deleteArticle(articleEntity: SavedArticleEntity) {
        viewModelScope.launch { localNewsSource.deleteArticle(articleEntity) }
    }

    /**
     * Removes all saved articles from the Room database
     */
    fun deleteAllArticles() {
        viewModelScope.launch { localNewsSource.deleteAllArticles() }
    }

}