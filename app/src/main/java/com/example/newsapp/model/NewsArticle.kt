package com.example.newsapp.model

import com.google.gson.annotations.SerializedName

// Class to model the response JSON return from the news API. Contains a list of articles.
data class APIResponse(
    @SerializedName("results")
    val results: List<NewsArticle>
)

// Class to model an individual news article.
data class NewsArticle(
    @SerializedName("title") val title: String,
    @SerializedName("link") val link: String,
    @SerializedName("creator") val creator: List<String>,
    @SerializedName("description") val description: String,
    @SerializedName("pubDate") val pubDate: String,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("source_id") val sourceID: String,
    @SerializedName("country") val country: List<String>,
    @SerializedName("category") val category: List<String>,
    @SerializedName("language") val language: String
)


