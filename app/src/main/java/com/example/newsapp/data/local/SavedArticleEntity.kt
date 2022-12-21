package com.example.newsapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.newsapp.Constants.Companion.SAVED_ARTICLE_TABLE
import com.example.newsapp.model.NewsArticle

/** A table in the Room database that stores individual articles bookmarked by the user.
 * Designed by following Android official tutorial (Room with a View):
* https://developer.android.com/codelabs/android-room-with-a-view-kotlin
 * @param artId - A unique ID for each article
 * @param article - News Article class object containing article details from API.
*/
@TypeConverters(ArticleConverter::class)
@Entity(tableName = SAVED_ARTICLE_TABLE)
class SavedArticleEntity(
    @PrimaryKey(autoGenerate = true) val artId: Int,
    @ColumnInfo(name = "article")
    val article: NewsArticle
)