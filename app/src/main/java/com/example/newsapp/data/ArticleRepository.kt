package com.example.newsapp.data

import com.example.newsapp.data.remote.RemoteNewsSource
import com.example.newsapp.model.APIResponse

// Intermediate layer between API handler and local database storage
class ArticleRepository (
    private val remoteNewsSource: RemoteNewsSource,
    //private val localNewsSource: LocalNewsSource
) {

    suspend fun getLatestNews(): APIResponse {
            val articles = remoteNewsSource.getLatestNews()
//            localNewsSource.updateDatabase()
//        } catch (e: Exception) {
//            Log.d("News Repo", "Connection to remote API failed, using local source")
//        }
        return remoteNewsSource.getLatestNews()
    }

    suspend fun getCategoryNews(category: String): APIResponse {
        return remoteNewsSource.getCategoryNews(category)
    }

    suspend fun searchNews(query: String): APIResponse {
        return remoteNewsSource.getCategoryNews(query)
    }

}