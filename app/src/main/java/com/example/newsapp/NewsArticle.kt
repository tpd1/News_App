package com.example.newsapp

class NewsArticle {
    private var headline: String? = null
    private var publisherIcon: Int = 0
    private var modelImage: Int = 0

    fun getHeadline():String {
        return headline.toString()
    }

    fun setHeadline(name: String) {
        this.headline = name
    }

    fun getPublisherIcon(): Int {
        return publisherIcon
    }

    fun setPublisherIcon(image_drawable: Int) {
        this.publisherIcon = image_drawable
    }

    fun getImages(): Int {
        return modelImage
    }

    fun setImages(image_drawable: Int) {
        this.modelImage = image_drawable
    }
}