package com.example.newsapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
//import com.example.newsapp.model.Article
//
///**
// * Handles inserting and fetching articles from local Room database.
// * Adapted from android developer documentation "https://developer.android.com/training/data-storage/room"
// */
//@Dao
//interface ArticleDBDao {
//
//    @Delete
//    suspend fun deleteArticle(article: Article)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertArticle(article: Article)
//
//    @Query("SELECT * FROM articles ORDER BY articleID")
//    fun fetchArticles(): LiveData<Article>
//
//}