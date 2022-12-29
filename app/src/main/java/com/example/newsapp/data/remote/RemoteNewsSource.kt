package com.example.newsapp.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.newsapp.Constants
import com.example.newsapp.Constants.Companion.MAX_ARTICLES
import com.example.newsapp.Constants.Companion.PAGE_SIZE
import com.example.newsapp.data.CategoryPagingSource
import com.example.newsapp.data.SearchPagingSource
import com.example.newsapp.model.APIResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interacts with the API service and uses Paging3 library to handle pagination of API calls.
 * Pagination functions designed by following a tutorial by Vikas Kumar:
 * https://medium.com/swlh/paging3-recyclerview-pagination-made-easy-333c7dfa8797
 * @param newsAPIHandler Instance of Retrofit interface for making calls to NewsAPI
 * @param filterResults Decides if the articles returned will be filtered by imageURL
 */
class RemoteNewsSource(
    private val newsAPIHandler: NewsApiService,
    var filterResults: Boolean
) {

    /**
     * Handles pagination of fetching news by category.
     * @param category The selected news topic
     */
    fun getPagingCategoryNews(category: String) =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = MAX_ARTICLES,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { CategoryPagingSource(newsAPIHandler, category, filterResults) }
        ).liveData

    /**
     * Handles pagination of fetching news by search query.
     * @param q The search term
     */
    fun getPagingSearchNews(q: String) =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = MAX_ARTICLES,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { SearchPagingSource(newsAPIHandler, q, filterResults) }
        ).liveData
}

/**
 * structures the calls to the NewsData.io for topics and handles the response using Retrofit library.
 */
interface NewsApiService {
    // Fetches news based on the category selected. By default searches UK news articles.
    @GET("news?")
    suspend fun getCategoryNews(
        @Query("category") category: String, // one of 7 categories shown in tabs.
        @Query("country") country: String = Constants.COUNTRY_CODE,
        @Query("language") language: String = Constants.LANGUAGE,
        @Query("page") page: Int,
        @Query("apikey") key: String = Constants.NEWS_API_KEY
    ): APIResponse

    // Function to fetch news articles based on a search term
    @GET("news?")
    suspend fun searchNews(
        @Query("q") query: String, // search term
        @Query("language") language: String = Constants.LANGUAGE,
        @Query("page") page: Int,
        @Query("apikey") key: String = Constants.NEWS_API_KEY
    ): APIResponse
}