package com.example.newsapp

/**
 * Stores frequently uses strings as constants to be used across the app.
 */
class Constants {
    // (companion object so we don't need to create an instance of the class)
    companion object {
        // NewsData.io API values
        const val NEWS_API_KEY = "pub_14675c68e5438ae4a7b999feff9379dc0b3e7"
        const val ROOT_API_URL = "https://newsdata.io/api/1/"
        const val COUNTRY_CODE = "gb"
        const val LANGUAGE = "en"
        const val PAGE_SIZE = 10
        const val MAX_ARTICLES = 30 // Max articles shown in recyclerview

        // Weather API values
        const val WEATHER_API_KEY = "21c4fa21cb21458f86e100153222712"
        const val WEATHER_API_URL = "https://api.weatherapi.com/v1/"

        // Topics for selection & tab layout
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
        const val TOP = "top"

        // App Setting Names
        const val WEATHER_LOCATION = "weather location"
        const val FILTER_IMAGES = "Filter results"

        // Data storage constants
        const val PREF_DATASTORE_NAME = "settings"
        const val SAVED_NEWS_DATABASE = "saved_article_database"
        const val SAVED_ARTICLE_TABLE = "saved_articles_table"

        // Error messages
        const val ERROR_400 = "Location Not Found"
        const val ERROR_401 = "API Key Not Valid"
        const val UNKNOWN_ERROR = "Unknown Error"
        const val NOT_MATCHING = "Passwords do not match"
        const val LOGIN_UNSUCCESSFUL = "Email or password incorrect"
        const val UNCHANGED_NAME = "New name must be different to current name"
        const val NON_VALID_EMAIL = "Email must be valid & non-empty"

        // Misc strings
        const val BOOKMARK_ADDED = "Bookmark added successfully"
        const val BOOKMARK_ALREADY_SAVED = "Bookmark already saved."
        const val ENTER_PASSWORD = "Password Must be over 6 characters long"
        const val LOGIN_SUCCESS = "Login Successful"
        const val BOOKMARK_REMOVED = "Bookmark Removed"
        const val ALL_BOOKMARKS_REM = "All Bookmarks Removed"
        const val DEFAULT_LOCATION = "London"
        const val NAME_SUCCESS = "Name Updated Successfully"
        const val PASSWORD_SUCCESS = "Password Updated Successfully"
        const val DIALOG_DELETE_ACC = "Permanently Delete Your Account?"

    }
}
