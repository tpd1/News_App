package com.example.newsapp

import com.example.newsapp.data.remote.NewsApiService
import com.example.newsapp.data.remote.RemoteNewsSource
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// Container for dependencies used in repository, to ensure they are shared across app.
class UtilsContainer {

    //Create a GsonConverter - passed to retrofit builder
    private val gsonConverterFactory = GsonConverterFactory.create()

    // use OkHttpClient when creating retrofit instance
    private val httpClient = OkHttpClient.Builder()
        .readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS)
        .build()

    // Create a retrofit instance
    private val retrofitInstance =
        Retrofit.Builder()
            .baseUrl(Constants.ROOT_API_URL)
            .client(httpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()

    // use created retrofit instance with functions created in NewsApiService class.
    private val api: NewsApiService = retrofitInstance.create(NewsApiService::class.java)

    // Only this is exposed, needs to be passed to ViewModel
    val remoteDataSource = RemoteNewsSource(api)


}