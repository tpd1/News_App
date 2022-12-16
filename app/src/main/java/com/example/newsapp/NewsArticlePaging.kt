//package com.example.newsapp
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.example.newsapp.database.NewsAPIHandler
//import com.example.newsapp.model.NewsArticle
//import retrofit2.HttpException
//import java.io.IOException
//
//class NewsArticlePaging(
//    private val newsAPIHandler: NewsAPIHandler,
//) : PagingSource<Int, NewsArticle>() {
//
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsArticle> {
////        val index = params.key ?: 1 //If null load page 1.
////
////        return try {
////            val response = newsAPIHandler.getLatestNews()
////            val articles = response.body()?.results
////
////            LoadResult.Page(
////                data = articles,
////                prevKey = if (index == 1) null else index - 1,
////                nextKey = if (articles.isEmpty()) null else index + 1
////            )
////        } catch (e: IOException) {
////            LoadResult.Error(e)
////        } catch (e: HttpException) {
////            LoadResult.Error(e)
////        }
//
//    }
//
//    // If news articles are updated whilst paging. This method is taken from android documentation
//    // Example code: https://developer.android.com/reference/kotlin/androidx/paging/PagingSource
//    override fun getRefreshKey(state: PagingState<Int, NewsArticle>): Int? {
//        return state.anchorPosition?.let {
//            state.closestPageToPosition(it)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
//        }
//    }
//
//}