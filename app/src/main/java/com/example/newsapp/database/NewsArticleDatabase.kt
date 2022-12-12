package com.example.newsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.Constants.Companion.NEWS_DATABASE_NAME
import com.example.newsapp.model.Article

/**
 * Models the Room database for storing articles
 * Adapted from android Sunflower example
 * 'https://github.com/android/sunflower'
 * and official android docs
 */
@TypeConverters(SourceConverter::class)
@Database(entities = [Article::class], version = 1)
abstract class NewsArticleDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDBDao
    // To create the database
    companion object {
        @Volatile
        private var dbInstance: NewsArticleDatabase? = null

        // Called whenever we create a database.
        // If database is null, call createDB function.
        operator fun invoke(context: Context) = dbInstance ?: synchronized(this) {
            dbInstance ?: createDB(context).also { dbInstance = it }
        }

        private fun createDB(context: Context) =
            Room.databaseBuilder(context.applicationContext, NewsArticleDatabase::class.java, NEWS_DATABASE_NAME)
                .build()
    }



}
