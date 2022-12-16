package com.example.newsapp.data

import com.example.newsapp.data.local.LocalNewsSource
import com.example.newsapp.data.remote.RemoteNewsSource

// Intermediate layer between API handler and local database storage
class ArticleRepository constructor(
    private val remoteNewsSource: RemoteNewsSource,
    private val localNewsSource: LocalNewsSource
) {


}