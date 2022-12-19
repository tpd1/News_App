package com.example.newsapp

import com.example.newsapp.data.ArticleRepository
import com.example.newsapp.data.remote.NewsApiService
import com.example.newsapp.data.remote.RemoteNewsSource
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// Container for dependencies used in repository, to ensure they are shared across app.
class UtilsContainer {



    private val gsonConverterFactory = GsonConverterFactory.create()

    private val httpClient = OkHttpClient.Builder()
        .readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS)
        .build()

    private val retrofitInstance =
        Retrofit.Builder()
            .baseUrl(Constants.ROOT_API_URL)
            .client(httpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()

    private val api: NewsApiService = retrofitInstance.create(NewsApiService::class.java)
    private val remoteDataSource = RemoteNewsSource(api)

    // Only accessible variable is the repository (used by the fragment to create a ViewModel)
    val newsRepo = ArticleRepository(remoteDataSource)

}