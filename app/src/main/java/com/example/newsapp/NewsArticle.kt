package com.example.newsapp

class NewsArticle {
    private var headline: String? = null
    private var content: String? = null
    private var modelImage: Int = 0

    fun getHeadline():String {
        return headline.toString()
    }

    fun setHeadline(name: String) {
        this.headline = name
    }

    fun getContent(): String {
        return content.toString()
    }

    fun setContent(content: String) {
        this.content = content
    }

    fun getImages(): Int {
        return modelImage
    }

    fun setImages(image_drawable: Int) {
        this.modelImage = image_drawable
    }
}