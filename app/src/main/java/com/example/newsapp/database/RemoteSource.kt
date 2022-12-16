package com.example.newsapp.database

import com.example.newsapp.model.APIResponse
import javax.inject.Inject


class RemoteSource @Inject constructor(
    private val newsAPIHandler: NewsAPIHandler //Dependency passed using Dagger, defined in modules class
) {

    suspend fun getLatestNews(): APIResponse {
        return newsAPIHandler.getLatestNews()
    }

    suspend fun getCategoryNews(category: String): APIResponse {
        return newsAPIHandler.getCategoryNews(category)
    }

    suspend fun getQueryNews(q: String): APIResponse {
        return newsAPIHandler.searchNews(q)
    }
}