package com.example.newsapp.model

import com.example.newsapp.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// structures the calls to the API for topics and handles the response. Uses Retrofit library.
interface APIHandler {

    // Uses co-routines to run on a background thread. For fetching top headlines from all categories
    @GET("/v2/top-headlines")
    suspend fun getTopNews (
        @Query("country") country: String = Constants.COUNTRY_CODE,
        @Query("page") pageNum: Int = 1,
        @Query("apiKey") key: String = Constants.API_KEY
    ) : Response<Article>

    // Fetches news based on the category selected. By default searches UK news articles.
    @GET("/v2/top-headlines")
    suspend fun getCategoryNews (
        @Query("category") category: String, // one of 7 categories shown in tabs.
        @Query("country") country: String = Constants.COUNTRY_CODE,
        @Query("page") pageNum: Int = 1,
        @Query("apiKey") key: String = Constants.API_KEY
    ) : Response<APIResponse>


    // Uses co-routines to run on a background thread.
    @GET("/v2/everything")
    suspend fun getSearchNews (
        @Query("q") query: String, // The search term
        @Query("page") pageNum: Int = 1,
        @Query("apiKey") key: String = Constants.API_KEY
    ) : Response<APIResponse>
}