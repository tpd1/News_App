package com.example.newsapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.example.newsapp.Constants.Companion.BUSINESS
import com.example.newsapp.Constants.Companion.CHANNEL_BUSINESS_ID
import com.example.newsapp.Constants.Companion.CHANNEL_ENTERTAINMENT_ID
import com.example.newsapp.Constants.Companion.CHANNEL_ENVIRONMENT_ID
import com.example.newsapp.Constants.Companion.CHANNEL_FOOD_ID
import com.example.newsapp.Constants.Companion.CHANNEL_HEALTH_ID
import com.example.newsapp.Constants.Companion.CHANNEL_POLITICS_ID
import com.example.newsapp.Constants.Companion.CHANNEL_SCIENCE_ID
import com.example.newsapp.Constants.Companion.CHANNEL_SPORTS_ID
import com.example.newsapp.Constants.Companion.CHANNEL_TECHNOLOGY_ID
import com.example.newsapp.Constants.Companion.CHANNEL_TOP_ID
import com.example.newsapp.Constants.Companion.ENTERTAINMENT
import com.example.newsapp.Constants.Companion.ENVIRONMENT
import com.example.newsapp.Constants.Companion.FOOD
import com.example.newsapp.Constants.Companion.HEALTH
import com.example.newsapp.Constants.Companion.POLITICS
import com.example.newsapp.Constants.Companion.SCIENCE
import com.example.newsapp.Constants.Companion.SPORTS
import com.example.newsapp.Constants.Companion.TECHNOLOGY
import com.example.newsapp.Constants.Companion.TOP
import com.example.newsapp.data.remote.RemoteNewsSource
import com.example.newsapp.model.NewsArticle
import com.example.newsapp.model.SettingsViewModel
import com.example.newsapp.ui.ArticleFragmentArgs
import com.example.newsapp.ui.MainActivity


class NotificationControl(
    base: Context,
    private val settingsViewModel: SettingsViewModel,
    private val remoteSource: RemoteNewsSource
) : ContextWrapper(base) {
    val subscribedChannels =
        mutableMapOf(
            TOP to true,
            BUSINESS to true,
            ENTERTAINMENT to true,
            ENVIRONMENT to true,
            FOOD to true,
            HEALTH to true,
            POLITICS to true,
            SCIENCE to true,
            SPORTS to true,
            TECHNOLOGY to true
        )

    private val manager: NotificationManager
        get() =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    init {
        createTopicChannels()
    }

    private fun createTopicChannels() {
        val topChannel =
            NotificationChannel(CHANNEL_TOP_ID, TOP, NotificationManager.IMPORTANCE_DEFAULT)
        val businessChannel = NotificationChannel(
            CHANNEL_BUSINESS_ID, BUSINESS, NotificationManager.IMPORTANCE_DEFAULT
        )
        val entertainmentChannel = NotificationChannel(
            CHANNEL_ENTERTAINMENT_ID, ENTERTAINMENT, NotificationManager.IMPORTANCE_DEFAULT
        )
        val environmentChannel = NotificationChannel(
            CHANNEL_ENVIRONMENT_ID, ENVIRONMENT, NotificationManager.IMPORTANCE_DEFAULT
        )
        val foodChannel =
            NotificationChannel(CHANNEL_FOOD_ID, FOOD, NotificationManager.IMPORTANCE_DEFAULT)
        val healthChannel =
            NotificationChannel(CHANNEL_HEALTH_ID, HEALTH, NotificationManager.IMPORTANCE_DEFAULT)
        val politicsChannel = NotificationChannel(
            CHANNEL_POLITICS_ID, POLITICS, NotificationManager.IMPORTANCE_DEFAULT
        )
        val scienceChannel =
            NotificationChannel(CHANNEL_SCIENCE_ID, SCIENCE, NotificationManager.IMPORTANCE_DEFAULT)
        val sportsChannel =
            NotificationChannel(CHANNEL_SPORTS_ID, SPORTS, NotificationManager.IMPORTANCE_DEFAULT)
        val technologyChannel = NotificationChannel(
            CHANNEL_TECHNOLOGY_ID, TECHNOLOGY, NotificationManager.IMPORTANCE_DEFAULT
        )

        manager.createNotificationChannels(
            listOf(
                topChannel, businessChannel, entertainmentChannel, environmentChannel, foodChannel,
                healthChannel, politicsChannel, scienceChannel, sportsChannel, technologyChannel
            )
        )

    }

    fun postNews(newsArticle: NewsArticle) {
        val contentText = newsArticle.title
        val category = newsArticle.category

        val builder = NotificationCompat.Builder(this, CHANNEL_TOP_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Latest $category News")
            .setContentText(contentText)
            .setContentIntent(navigateToArticle(newsArticle))

        with(NotificationManagerCompat.from(this)) {
            notify(101, builder.build())
        }
    }

    /**
     * sets up a pending intent to pass an article to the article viewer fragment. Made easier by the use
     * of a Nav Graph in the project - uses deep linking to pass the article the same way as the newsfeed currently does.
     * Credit https://stackoverflow.com/a/55245317
     */
    private fun navigateToArticle(article: NewsArticle): PendingIntent {
        val args = ArticleFragmentArgs(article)
        // Access the nav graph already implemented for the project, use deep linking to easily pass the article
        return NavDeepLinkBuilder(this)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.news_nav)
            .setDestination(R.id.articleFragment)
            .setArguments(args.toBundle())
            .createPendingIntent()
    }

    suspend fun getAPIArticle(category: String): NewsArticle {
        val response = remoteSource.getNotificationNews(category)
        return response.results[0]
    }
}

