package com.example.newsapp

import android.content.Context
import androidx.room.Room
import com.example.newsapp.data.local.ArticleDao
import com.example.newsapp.data.local.LocalNewsSource
import com.example.newsapp.data.local.SavedArticleDatabase
import com.example.newsapp.data.remote.NewsApiService
import com.example.newsapp.data.remote.RemoteNewsSource
import com.example.newsapp.ui.MainActivity
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// Container for dependencies used in repository, to ensure they are shared across app.
class UtilsContainer {

    //Create a GsonConverter - passed to retrofit builder
    private val gsonConverterFactory = GsonConverterFactory.create()

    // use OkHttpClient when creating retrofit instance
    private val httpClient = OkHttpClient.Builder()
        .readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS)
        .build()

    // Create a retrofit instance
    private val retrofitInstance =
        Retrofit.Builder()
            .baseUrl(Constants.ROOT_API_URL)
            .client(httpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()

    // use created retrofit instance with functions created in NewsApiService class.
    private val api: NewsApiService = retrofitInstance.create(NewsApiService::class.java)
    val remoteDataSource = RemoteNewsSource(api)

    /**
     * Performs the setup for the saved articles Room database.
     * @param context application context passed by the
     */
    fun setUpDatabase(context: Context) : LocalNewsSource {
        val savedArticleDatabase = Room.databaseBuilder(
            context.applicationContext, SavedArticleDatabase::class.java, Constants.SAVED_NEWS_DATABASE
        ).build()

        val articleDao = savedArticleDatabase.articleDao()

        return LocalNewsSource(articleDao)
    }




}

/**
 * Room doesn't allow custom classes to be stored. We need to convert Article to String first.
 */
//class TypeConverters {

//    private var gSonConverter = Gson()
//
//    @TypeConverter
//    fun newsArticleToString(newsArticle: NewsArticle): String {
//        return gSonConverter.toJson(newsArticle)
//    }
//
//    @TypeConverter
//    fun stringToNewsArticle(jsonData: String): NewsArticle {
//        return gSonConverter.toJson(newsArticle)
//    }
//}