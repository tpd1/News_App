package com.example.newsapp.ui


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
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
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Main activity in which most other functionality of the app is instantiated.
 * Provides a base for other fragments to access variables that exist for the lifetime
 * of the activity.
 */

class MainActivity : AppCompatActivity() {
    // Utils container creates one instance of dependencies for other classes across app.
    val utilsContainer = UtilsContainer()

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

    @OptIn(DelicateCoroutinesApi::class)
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


        val broadCastReceiver = object : BroadcastReceiver() {
            override fun onReceive(contxt: Context, intent: Intent) {
                    GlobalScope.launch {
                        notificationController.getArticle()
                    }

                }
            }
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(broadCastReceiver, IntentFilter("getArticle"))

//        val receiver = NotificationReceiver(notificationController)
//        val intentFilter = IntentFilter("getArticle")
//        registerReceiver(receiver, intentFilter)

        scheduleNotifications()
    }


    private fun scheduleNotifications() {
        Log.d("Function called", "scheduleNotificaitons called")

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.action = "getArticle"
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Set up the alarm to trigger every hour
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            AlarmManager.INTERVAL_HOUR,
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

//class NotificationReceiver(private val notificationControl: NotificationControl) : BroadcastReceiver() {
//    @OptIn(DelicateCoroutinesApi::class)
//    override fun onReceive(context: Context, intent: Intent) {
//        Log.d("Function called", "onreceived called")
//            GlobalScope.launch {
//                notificationControl.getArticle()
//            }
//    }
//
//}
