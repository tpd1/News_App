package com.example.newsapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.data.remote.NewsApiService
import com.example.newsapp.model.NewsArticle
import java.io.IOException

private const val START_INDEX = 1

/**
 * Class to provide a source for the paging of categorised news articles in the recyclerview.
 * Designed by following tutorials by Florian Walther and Android code-labs:
 * https://github.com/codinginflow/ImageSearchApp
 * @property newsApiService Interface that shapes queries to the API
 * @property category For fetching category news.
 * @property filterResults Whether the results will be filtered by having no imageURL.
 */
class CategoryPagingSource (
    private val newsApiService: NewsApiService,
    private val category: String,
    private val filterResults: Boolean
        ) : PagingSource<Int, NewsArticle>() {

    /**
     * For handling the last accessed position in the list. Not used in this project.
     */
    override fun getRefreshKey(state: PagingState<Int, NewsArticle>): Int? {
        return null
    }

    /***
     * Loads items into the list
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsArticle> {
        val pos = params.key ?: START_INDEX
        return try {
            val response = newsApiService.getCategoryNews(category, page = pos)
            val articles = response.results
            val filteredArticles: List<NewsArticle> = if (filterResults) {
                articles.filter { it.imageUrl != null }
            } else {
                articles
            }
            LoadResult.Page(
                data = filteredArticles,
                prevKey = if (pos == START_INDEX) null else pos - 1, //Ensure start key is lowest key
                nextKey = if (articles.isEmpty()) null else pos + 1  // When we reach end of API results
            )
        } catch (e: IOException) { LoadResult.Error(e) }
    }
}