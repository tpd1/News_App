package com.example.newsapp.notifications


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
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
import com.example.newsapp.R
import com.example.newsapp.data.remote.RemoteNewsSource
import com.example.newsapp.model.NewsArticle
import com.example.newsapp.model.SettingsViewModel
import com.example.newsapp.ui.ArticleFragmentArgs
import com.example.newsapp.ui.MainActivity

/**
 *
 * This class was implemented following the tutorial below (in addition to lecture recording):
 * https://www.kodeco.com/1214490-android-notifications-tutorial-getting-started
 */
class NotificationControl(
    base: Context,
    private val settingsViewModel: SettingsViewModel,
    private val remoteSource: RemoteNewsSource
) : ContextWrapper(base) {

    // A list of topics that the user has already selected, with a key that can be to send notifications.
    private val subscribedTopics = hashMapOf(
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

    // Based on the notification setting in App Settings. Initialise to 'All notifications' as the update coroutine
    // finishes before any notifications are sent.
    private val notificationSetting = MutableLiveData(ALL)

    private val manager: NotificationManager
        get() =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    init {
        createTopicChannels()
    }

    /**
     * Creates two notification channels: One for all notifications and one for user selected notifications.
     */
    private fun createTopicChannels() {
        // Channel for top news (covers all categories)
        val topChannel =
            NotificationChannel(CHANNEL_TOP_ID, TOP, NotificationManager.IMPORTANCE_DEFAULT)
        // Channel for just the topics the user has selected.
        val selectedChannel =
            NotificationChannel(CHANNEL_SELECTED_ID, SELECTED, NotificationManager.IMPORTANCE_DEFAULT)
        manager.createNotificationChannels(listOf(topChannel, selectedChannel))
    }


    /**
     * Top level function that posts a notification to the user based on the notification settings.
     */
    suspend fun getArticle() {
        if (notificationSetting.value == null) {
            return
        }
        val article: NewsArticle
        when (notificationSetting.value) {
            // If the user has "All Topics" chose, send a Top news notification.
            ALL -> {
                article = getCategoryArticle(TOP)
                postNews(article, CHANNEL_TOP_ID, TOP)
            }
            // If the user has "Selected Topics" chosen, pick a random topic from their possible options.
            SELECTED -> {
                if (subscribedTopics.values.all { it.value == false }) { //If no topics selected, otherwise causes crash
                    return
                } else {
                    val category = chooseRandomTopic()
                    article = getCategoryArticle(category)
                    postNews(article, CHANNEL_SELECTED_ID, category)
                }
            }
        }

    }

    /**
     * Out of the user subscribed topics, chooses one at random.
     * @return The selected topic string.
     */
    private fun chooseRandomTopic(): String {
        return if (subscribedTopics.size > 0) {
            subscribedTopics.filterValues { it.value!! }.keys.shuffled().first()
        } else {
            TOP // If the user has not selected any topics, show trending/top news.
        }
    }

    /**
     * Helper function that builds the notification to be sent.
     * @param newsArticle The news article to be posted
     * @param channelID The channel that the notification will be posted to.
     * @param category The category of the news article.
     */
    private fun postNews(newsArticle: NewsArticle, channelID: String, category: String) {
        val contentText = newsArticle.title
        // Pass category here - news API returns array of categories for each article, so is usually not accurate.
        val capCategory = category.replaceFirstChar { it.uppercase() }
        val builder = NotificationCompat.Builder(this, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Latest $capCategory News")
            .setContentText(contentText)
            .setStyle(NotificationCompat.BigTextStyle())
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
        subscribedTopics[BUSINESS]?.value = businessEnabled
        subscribedTopics[ENTERTAINMENT]?.value = entertainmentEnabled
        subscribedTopics[ENVIRONMENT]?.value = environmentEnabled
        subscribedTopics[FOOD]?.value = foodEnabled
        subscribedTopics[HEALTH]?.value = healthEnabled
        subscribedTopics[POLITICS]?.value = politicsEnabled
        subscribedTopics[SCIENCE]?.value = scienceEnabled
        subscribedTopics[SPORTS]?.value = sportsEnabled
        subscribedTopics[TECHNOLOGY]?.value = technologyEnabled
    }

    /**
     * Updates the user notification settings (All / Selected / None)
     */
    fun updateNotificationSettings() {
        val setting = settingsViewModel.notifications.value
        notificationSetting.value = setting
    }

}



