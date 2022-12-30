package com.example.newsapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDeepLinkBuilder
import com.example.newsapp.Constants.Companion.ALL
import com.example.newsapp.Constants.Companion.BUSINESS
import com.example.newsapp.Constants.Companion.CHANNEL_SELECTED_ID
import com.example.newsapp.Constants.Companion.CHANNEL_TOP_ID
import com.example.newsapp.Constants.Companion.ENTERTAINMENT
import com.example.newsapp.Constants.Companion.ENVIRONMENT
import com.example.newsapp.Constants.Companion.FOOD
import com.example.newsapp.Constants.Companion.HEALTH
import com.example.newsapp.Constants.Companion.POLITICS
import com.example.newsapp.Constants.Companion.SCIENCE
import com.example.newsapp.Constants.Companion.SELECTED
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

    private val subscribedChannels = hashMapOf(
        BUSINESS to MutableLiveData(true),
        ENTERTAINMENT to MutableLiveData(true),
        ENVIRONMENT to MutableLiveData(true),
        FOOD to MutableLiveData(true),
        HEALTH to MutableLiveData(true),
        POLITICS to MutableLiveData(true),
        SCIENCE to MutableLiveData(true),
        SPORTS to MutableLiveData(true),
        TECHNOLOGY to MutableLiveData(true)
    )

    private val notificationSetting = MutableLiveData(ALL)

    private val manager: NotificationManager
        get() =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    init {
        createTopicChannels()
    }

    private fun createTopicChannels() {
        val topChannel =
            NotificationChannel(CHANNEL_TOP_ID, TOP, NotificationManager.IMPORTANCE_DEFAULT)
        val selectedChannel =
            NotificationChannel(CHANNEL_SELECTED_ID, SELECTED, NotificationManager.IMPORTANCE_DEFAULT)

        manager.createNotificationChannels(listOf(topChannel, selectedChannel))
    }

    suspend fun getArticle() {
        if (notificationSetting.value == null) {
            return
        }
        val article: NewsArticle
        when (notificationSetting.value) {
            ALL -> {
                Log.i("Top Topic", "top topic selected")
                article = getCategoryArticle(TOP)
                postNews(article, CHANNEL_TOP_ID)
            }
            SELECTED -> {
                Log.i("Random Topic", "random topic selected")
                val category = chooseRandomTopic()
                article = getCategoryArticle(category)
                postNews(article, CHANNEL_SELECTED_ID)
            }
            else -> {
                // Do nothing
            }
        }

    }

    private fun chooseRandomTopic(): String {
        return subscribedChannels.filterValues { it.value!! }.keys.shuffled().first()
    }

    private fun postNews(newsArticle: NewsArticle, channelID: String) {
        val contentText = newsArticle.title
        val category = newsArticle.category[0] // Just use first topic from API

        val builder = NotificationCompat.Builder(this, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Latest $category News")
            .setContentText(contentText)
            .setContentIntent(pendingIntentHelper(newsArticle))

        with(NotificationManagerCompat.from(this)) {
            notify(101, builder.build())
        }
    }

    /**
     * sets up a pending intent to pass an article to the article viewer fragment. Made easier by the use
     * of a Nav Graph in the project - uses deep linking to pass the article the same way as the newsfeed currently does.
     * Credit https://stackoverflow.com/a/55245317
     * @param article The article to be passed.
     * @return A pending intent to the article viewer class, containing the article as an argument.
     */
    private fun pendingIntentHelper(article: NewsArticle): PendingIntent {
        val args = ArticleFragmentArgs(article)
        // Access the nav graph already implemented for the project, use deep linking to easily pass the article
        return NavDeepLinkBuilder(this)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.news_nav)
            .setDestination(R.id.articleFragment)
            .setArguments(args.toBundle())
            .createPendingIntent()
    }

    /**
     * Calls the remote source to fetch a single news story from the API.
     * @param category category of news story to fetch.
     * @return The news article object for that API response.
     */
    private suspend fun getCategoryArticle(category: String): NewsArticle {
        val response = remoteSource.getNotificationNews(category)
        return response.results.shuffled().first()
    }

    /**
     * Updates the hashmap of the user's topic settings.
     */
    fun updateSubscribedChannels() {
        // Fetch the toggle settings stored in the DataStore for each topic
        val businessEnabled = settingsViewModel.businessEnabled.value
        val entertainmentEnabled = settingsViewModel.entertainmentEnabled.value
        val environmentEnabled = settingsViewModel.environmentEnabled.value
        val foodEnabled = settingsViewModel.foodEnabled.value
        val healthEnabled = settingsViewModel.healthEnabled.value
        val politicsEnabled = settingsViewModel.politicsEnabled.value
        val scienceEnabled = settingsViewModel.scienceEnabled.value
        val sportsEnabled = settingsViewModel.sportsEnabled.value
        val technologyEnabled = settingsViewModel.technologyEnabled.value

        // Update the hashmap with these values.
        subscribedChannels[BUSINESS]?.value = businessEnabled
        subscribedChannels[ENTERTAINMENT]?.value = entertainmentEnabled
        subscribedChannels[ENVIRONMENT]?.value = environmentEnabled
        subscribedChannels[FOOD]?.value = foodEnabled
        subscribedChannels[HEALTH]?.value = healthEnabled
        subscribedChannels[POLITICS]?.value = politicsEnabled
        subscribedChannels[SCIENCE]?.value = scienceEnabled
        subscribedChannels[SPORTS]?.value = sportsEnabled
        subscribedChannels[TECHNOLOGY]?.value = technologyEnabled
    }

    fun updateNotificationSettings() {
        val setting = settingsViewModel.notifications.value
        notificationSetting.value = setting
        println(setting)
    }

}

