package com.example.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.example.newsapp.R
import com.example.newsapp.UtilsContainer
import com.example.newsapp.data.DataStoreRepo
import com.example.newsapp.data.local.LocalNewsSource
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.model.BookmarksViewModel
import com.example.newsapp.model.SettingsViewModel
import com.google.android.material.appbar.MaterialToolbar


/**
 * Main activity in which most other functionality of the app is instantiated.
 */

class MainActivity : AppCompatActivity() {

    // Utils container creates one instance of dependencies for other classes across app.
    val utilsContainer = UtilsContainer()

    // Create DataStore for persisting user settings
    private lateinit var dataStoreRepo: DataStoreRepo

    // Create ViewModel for storing settings to a DataStore, as we need it across the app.
    lateinit var settingsViewModel: SettingsViewModel

    // Create Room database for saving bookmarked articles offline.
    lateinit var bookmarksViewModel: BookmarksViewModel

    // Create Room database for saving bookmarked articles offline.
    private lateinit var localNewsSource: LocalNewsSource

    // NavController for navigation using NavGraph
    private lateinit var navController: NavController

    //Data binding for this activity
    private lateinit var mainBinding: ActivityMainBinding


    /**
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set up View binding for main activity
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

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

        // Initialise datastore repository and ViewModel for topic selection.
        dataStoreRepo = DataStoreRepo(this.applicationContext)
        settingsViewModel = SettingsViewModel(dataStoreRepo)

        // Initialise a local Room database and ViewModel for saving bookmarked articles.
        localNewsSource =
            utilsContainer.setUpDatabase(this.applicationContext) // tied to lifecycle of the application.
        bookmarksViewModel = BookmarksViewModel(localNewsSource)

        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.toolbar_top, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return NavigationUI.onNavDestinationSelected(menuItem, navController)
            }
        })
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

    //enable the up button on the nav controller.
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}