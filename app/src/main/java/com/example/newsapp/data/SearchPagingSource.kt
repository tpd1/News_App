package com.example.newsapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.data.remote.NewsApiService
import com.example.newsapp.model.NewsArticle
import java.io.IOException

private const val START_INDEX = 1

/**
 * Class to provide a source for the paging of news articles in the recyclerview.
 * Designed by following tutorials by Florian Walther and Android code-labs:
 * https://github.com/codinginflow/ImageSearchApp
 * @param newsApiService - interface that shapes queries to the API
 * @param q - For fetching category news.
 */
class SearchPagingSource(
    private val newsApiService: NewsApiService,
    private val q: String,
    private val filterResults: Boolean
) : PagingSource<Int, NewsArticle>() {

    override fun getRefreshKey(state: PagingState<Int, NewsArticle>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsArticle> {
        val pos = params.key ?: START_INDEX
        return try {
            val response = newsApiService.searchNews(q, page = pos)
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
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }
}
