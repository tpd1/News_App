package com.example.newsapp

class Constants {
    // (companion object so we don't need to create an instance of the class)
    companion object {
        const val API_KEY = "pub_14675c68e5438ae4a7b999feff9379dc0b3e7"
        const val ROOT_API_URL = "https://newsdata.io/api/1/"
        const val COUNTRY_CODE = "gb"
        const val LANGUAGE = "en"

        const val PREF_DATASTORE_NAME = "settings"
        const val BUSINESS = "business"
        const val ENTERTAINMENT = "entertainment"
        const val ENVIRONMENT = "environment"
        const val FOOD = "food"
        const val HEALTH = "health"
        const val POLITICS = "politics"
        const val SCIENCE = "science"
        const val SPORTS = "sports"
        const val TECHNOLOGY = "technology"
        const val TRENDING = "trending"

        const val NEWS_DATABASE_NAME = "news_database"
        const val ARTICLE_TABLE = "articles_table"
    }
}

data class Topics(
    val selectedTopics: MutableList<Categories>
)

// Contains the topic categories accepted by the API
enum class Categories(val catName: String) {

}

