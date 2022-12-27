package com.example.newsapp

import android.content.Context
import androidx.room.Room
import com.example.newsapp.data.local.LocalNewsSource
import com.example.newsapp.data.local.SavedArticleDatabase
import com.example.newsapp.data.remote.NewsApiService
import com.example.newsapp.data.remote.RemoteNewsSource
import com.example.newsapp.weather.RemoteWeatherSource
import com.example.newsapp.weather.WeatherApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// Container for dependencies used in repository, to ensure they are shared across app.
class UtilsContainer {

    //Create a GsonConverter - passed to retrofit builders
    private val gsonConverterFactory = GsonConverterFactory.create()

    // Use single OkHttpClient instance for both APIs
    private val httpClient = OkHttpClient.Builder()
        .readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS)
        .build()

    // Create a retrofit instance for news API.
    private val retrofitNews =
        Retrofit.Builder()
            .baseUrl(Constants.ROOT_API_URL)
            .client(httpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()

    // Create a retrofit instance for weather API.
    private val retrofitWeather =
        Retrofit.Builder()
            .baseUrl(Constants.WEATHER_API_URL)
            .client(httpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()

    // use created retrofit instance with functions created in NewsApiService class.
    private val api: NewsApiService = retrofitNews.create(NewsApiService::class.java)
    val remoteDataSource = RemoteNewsSource(api)

    private val weatherApi: WeatherApiService = retrofitWeather.create(WeatherApiService::class.java)
    val remoteWeatherSource = RemoteWeatherSource(weatherApi)

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