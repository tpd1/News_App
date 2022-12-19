package com.example.newsapp.data.local

import com.example.newsapp.model.APIResponse

class LocalNewsSource {

    suspend fun updateDatabase() {}

    suspend fun getNews() : APIResponse? {
        return null
    }
}
