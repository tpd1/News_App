package com.example.newsapp.data.remote

import com.example.newsapp.Constants
import com.example.newsapp.model.APIResponse
import retrofit2.http.GET
import retrofit2.http.Query

// Accessed by ArticleViewModel class to interact with retrofit and fetch articles.
// Gets passed NewsAPIService from MainActivity, which contains a retrofit instance.
class RemoteNewsSource(private val newsAPIHandler: NewsApiService) {

    suspend fun getLatestNews(): APIResponse = newsAPIHandler.getLatestNews()

    suspend fun getCategoryNews(category: String): APIResponse =
        newsAPIHandler.getCategoryNews(category)


    suspend fun getQueryNews(q: String): APIResponse =
        newsAPIHandler.searchNews(q)

}

// structures the calls to the NewsData.io for topics and handles the response. Uses Retrofit library.
// Uses co-routines with suspend functions. If this was executed on main UI thread
// It would freeze whilst waiting for the api response.
interface NewsApiService {

    // For fetching top headlines from all categories, in the 'Trending' tab
    @GET("news?")
    suspend fun getLatestNews(
        @Query("country") country: String = Constants.COUNTRY_CODE,
        @Query("language") language: String = Constants.LANGUAGE,
        @Query("page") pageNum: Int = 1,
        @Query("apikey") key: String = Constants.API_KEY
    ): APIResponse

    // Fetches news based on the category selected. By default searches UK news articles.
    @GET("news?")
    suspend fun getCategoryNews(
        @Query("category") category: String, // one of 7 categories shown in tabs.
        @Query("country") country: String = Constants.COUNTRY_CODE,
        @Query("language") language: String = Constants.LANGUAGE,
        @Query("page") pageNum: Int = 1,
        @Query("apikey") key: String = Constants.API_KEY
    ): APIResponse


    // Function to fetch news articles based on a search term
    @GET("/v2/everything")
    suspend fun searchNews(
        @Query("q") query: String, // The search term
        @Query("page") pageNum: Int = 1,
        @Query("apikey") key: String = Constants.API_KEY,
        @Query("language") language: String = Constants.LANGUAGE
    ): APIResponse
}