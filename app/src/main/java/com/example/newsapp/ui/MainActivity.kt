package com.example.newsapp.ui


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.Constants.Companion.INTENT_ACTION
import com.example.newsapp.notifications.NotificationControl
import com.example.newsapp.R
import com.example.newsapp.UtilsContainer
import com.example.newsapp.data.DataStoreRepo
import com.example.newsapp.data.local.LocalNewsSource
import com.example.newsapp.data.remote.RemoteNewsSource
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.model.BookmarksViewModel
import com.example.newsapp.model.NewsViewModel
import com.example.newsapp.model.SettingsViewModel
import com.example.newsapp.notifications.NotificationReceiver
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar


/**
 * Main activity in which most other functionality of the app is instantiated.
 * Provides a base for other fragments to access variables that exist for the lifetime
 * of the activity.
 */

class MainActivity : AppCompatActivity() {
    // Utils container creates one instance of dependencies for other classes across app.
    val utilsContainer = UtilsContainer()

    private var receiver: NotificationReceiver = NotificationReceiver()

    // Create ViewModel for storing settings to a DataStore, as we need it across the app.
    lateinit var settingsViewModel: SettingsViewModel

    // Create Room database for saving bookmarked articles offline.
    lateinit var bookmarksViewModel: BookmarksViewModel

    // Store news view model here to access "filter null images" toggle.
    lateinit var newsViewModel: NewsViewModel

    // Create room database for saving bookmarked articles offline.
    private lateinit var localNewsSource: LocalNewsSource

    // NavController for navigation using NavGraph
    private lateinit var navController: NavController

    //Data binding for this activity
    private lateinit var mainBinding: ActivityMainBinding

    // Notification controller for updating topics.
    lateinit var notificationController: NotificationControl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set up View binding for main activity
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        // Show login message if coming from login activity.
        val msg = intent.extras?.getString("msg")
        msg?.let { Snackbar.make(mainBinding.root, it, Snackbar.LENGTH_SHORT).show() }

        // Set up Remote API source for news. Create News View model as intermediate layer.
        val remoteNewsSource = RemoteNewsSource(utilsContainer.newsApi, true)
        newsViewModel = NewsViewModel(remoteNewsSource)

        // Initialise datastore repository and ViewModel for topic selection.
        val dataStoreRepo = DataStoreRepo(this.applicationContext)
        settingsViewModel = SettingsViewModel(dataStoreRepo)

        // Initialise class to control notifications.
        notificationController = NotificationControl(this, settingsViewModel, remoteNewsSource)

        // Set up bottom app navigation bar to access fragments.
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFrag) as NavHostFragment
        navController = navHostFragment.findNavController()

        // Set up top app bar
        val topAppBar: MaterialToolbar = mainBinding.topNavBar
        setSupportActionBar(topAppBar)
        topAppBar.setupWithNavController(navController)

        // set up bottom navigation bar:
        createBottomNavBar()

        // Initialise a local Room database and ViewModel for saving bookmarked articles.
        localNewsSource =
            utilsContainer.setUpDatabase(this.applicationContext) // tied to lifecycle of the application.
        bookmarksViewModel = BookmarksViewModel(localNewsSource)

        // Create top menu
        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.toolbar_top, menu)
            }

            // On menu item selected, navigate to that fragment
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return NavigationUI.onNavDestinationSelected(menuItem, navController)
            }
        })
        observeSettings()

        // Create a BroadcastReceiver to process notifications on an alarm. Initialise it here
        // so we can use the MainActivity context to access the NotificationControl class.
        receiver = NotificationReceiver()
        val filter = IntentFilter(INTENT_ACTION)
        registerReceiver(receiver, filter)

        scheduleNotifications()
    }

    /**
     *  Sets up periodic notifications to be pushed to the user twice per day.
     * Ref: https://developer.android.com/training/scheduling/alarms.html#type
     */
    private fun scheduleNotifications() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val notificationIntent = Intent(INTENT_ACTION)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Set up the alarm to trigger twice per day
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_FIFTEEN_MINUTES,
            AlarmManager.INTERVAL_HALF_DAY,
            pendingIntent
        )
    }

    /**
     * Helper function to set the bottom navigation bar with the NavController.
     */
    private fun createBottomNavBar() {
        AppBarConfiguration(
            setOf(
                R.id.newsFragment,
                R.id.weatherFragment,
                R.id.bookmarksFragment,
                R.id.profileFragment
            )
        )
        mainBinding.bottomNavView.setupWithNavController(navController)
        // If we load the full screen article view, hide the bottom nav bar for a cleaner look.
        navController.addOnDestinationChangedListener { _, frag, _ ->
            if (frag.id == R.id.articleFragment) {
                mainBinding.bottomNavView.visibility = View.GONE
            } else {
                mainBinding.bottomNavView.visibility = View.VISIBLE
            }
        }
    }

    // Enables the up button on the nav controller.
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    /**
     * Sets observers for the notification settings, to allow the controller to post correct notifications.
     */
    private fun observeSettings() {
        settingsViewModel.notifications.observe(this) {
            notificationController.updateNotificationSettings()
        }

        settingsViewModel.businessEnabled.observe(this) {
            notificationController.updateSubscribedChannels()
        }

        settingsViewModel.entertainmentEnabled.observe(this) {
            notificationController.updateSubscribedChannels()
        }

        settingsViewModel.environmentEnabled.observe(this) {
            notificationController.updateSubscribedChannels()
        }

        settingsViewModel.foodEnabled.observe(this) {
            notificationController.updateSubscribedChannels()
        }

        settingsViewModel.healthEnabled.observe(this) {
            notificationController.updateSubscribedChannels()
        }

        settingsViewModel.politicsEnabled.observe(this) {
            notificationController.updateSubscribedChannels()
        }

        settingsViewModel.scienceEnabled.observe(this) {
            notificationController.updateSubscribedChannels()
        }

        settingsViewModel.sportsEnabled.observe(this) {
            notificationController.updateSubscribedChannels()
        }

        settingsViewModel.technologyEnabled.observe(this) {
            notificationController.updateSubscribedChannels()
        }
    }
}

