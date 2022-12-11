package com.example.newsapp.model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true) var articleID: Int = 0,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "publishedAt") val publishedAt: String,
    @ColumnInfo(name = "source") val source: Source,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "urlToImage") val urlToImage: String
)