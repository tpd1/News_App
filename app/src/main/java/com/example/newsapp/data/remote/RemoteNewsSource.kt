package com.example.newsapp.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.newsapp.Constants
import com.example.newsapp.Constants.Companion.MAX_ARTICLES
import com.example.newsapp.Constants.Companion.PAGE_SIZE
import com.example.newsapp.data.ArticlePagingSource
import com.example.newsapp.model.APIResponse
import retrofit2.http.GET
import retrofit2.http.Query

// Accessed by ArticleViewModel class to interact with retrofit and fetch articles.
// Gets passed NewsAPIService from MainActivity, which contains a retrofit instance.
class RemoteNewsSource(private val newsAPIHandler: NewsApiService) {

   fun getPagingNews(q: String) =
       Pager(
           config = PagingConfig(
               pageSize = PAGE_SIZE,
               maxSize = MAX_ARTICLES,
               enablePlaceholders = true
           ),
           pagingSourceFactory = { ArticlePagingSource(newsAPIHandler, q) }
       ).liveData


//    suspend fun getQueryNews(q: String): APIResponse =
//        newsAPIHandler.searchNews(q)

}

// structures the calls to the NewsData.io for topics and handles the response. Uses Retrofit library.
// Uses co-routines with suspend functions. If this was executed on main UI thread
// It would freeze whilst waiting for the api response.
interface NewsApiService {

    // Fetches news based on the category selected. By default searches UK news articles.
    @GET("news?")
    suspend fun getCategoryNews(
        @Query("category") category: String, // one of 7 categories shown in tabs.
        @Query("country") country: String = Constants.COUNTRY_CODE,
        @Query("language") language: String = Constants.LANGUAGE,
        @Query("page") page: Int,
        @Query("apikey") key: String = Constants.API_KEY
    ): APIResponse


    // Function to fetch news articles based on a search term
    @GET("news?")
    suspend fun searchNews(
        @Query("q") query: String, // search term
        @Query("country") country: String = Constants.COUNTRY_CODE,
        @Query("language") language: String = Constants.LANGUAGE,
        @Query("page") page: Int,
        @Query("apikey") key: String = Constants.API_KEY
    ): APIResponse
}