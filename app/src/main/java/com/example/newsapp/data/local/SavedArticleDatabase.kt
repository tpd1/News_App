package com.example.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.newsapp.model.NewsArticle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


/**
 * Defines the database used to save articles locally, implementing a Room database.
 * Instantiated once in UtilsContainer to MainActivity class.
 *
 * Designed by following Android official tutorial (Room with a View):
 * https://developer.android.com/codelabs/android-room-with-a-view-kotlin
 */
@Database(entities = [SavedArticleEntity::class], version = 1, exportSchema = false)
@TypeConverters(ArticleConverter::class)
abstract class SavedArticleDatabase: RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}

class ArticleConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun newsArticleToString(article: NewsArticle) : String {
            val listType = object : TypeToken<NewsArticle>() {}.type
            return Gson().toJson(article, listType)
        }

        @TypeConverter
        @JvmStatic
        fun stringToNewsArticle(string: String): NewsArticle {
            return Gson().fromJson(string, NewsArticle::class.java)
        }
    }

}