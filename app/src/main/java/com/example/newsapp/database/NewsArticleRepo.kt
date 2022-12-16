package com.example.newsapp.database

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject
import javax.inject.Singleton

// Intermediate layer between API handler and local database storage
@ActivityRetainedScoped // Survives for the lifecycle of the main activity including configuration changes
class NewsArticleRepo @Inject constructor(remoteSource: RemoteSource) {

    val remote = remoteSource
}