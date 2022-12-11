package com.example.newsapp.model


import com.google.gson.annotations.SerializedName

data class APIResponse(
    @SerializedName("articles")
    val article: List<Article>,
    val status: String,
    val totalResults: Int
)