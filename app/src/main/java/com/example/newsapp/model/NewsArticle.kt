package com.example.newsapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Class to model the response JSON return from the news API. Contains a list of articles.
 */
data class APIResponse(
    @SerializedName("results")
    val results: List<NewsArticle>
)

/**
 * Class to model an individual news article.
 * Extends parcelable so it can be passed to article activity using nav graph args.
 */
@Parcelize
data class NewsArticle(
    @SerializedName("title") val title: String,
    @SerializedName("link") val link: String,
    @SerializedName("creator") val creator: List<String>?,
    @SerializedName("pubDate") val pubDate: String,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("source_id") val sourceID: String,
    @SerializedName("country") val country: List<String>,
    @SerializedName("category") val category: List<String>,
    @SerializedName("language") val language: String
) : Parcelable

