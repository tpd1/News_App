package com.example.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
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
import com.example.newsapp.model.TopicViewModel
import com.google.android.material.appbar.MaterialToolbar



/**
 * Main activity in which most other functionality of the app is instantiated.
 */

class MainActivity : AppCompatActivity() {

    // Utils container creates one instance of dependencies for other classes across app.
    val utilsContainer = UtilsContainer()
    // Create DataStore for persisting user settings
    private lateinit var dataStoreRepo: DataStoreRepo
    // Create ViewModel for topics fragment here, as we need it to set tabs in news fragment.
    lateinit var topicsViewModel: TopicViewModel
    // Create Room database for saving bookmarked articles offline.
    lateinit var localNewsSource: LocalNewsSource
    // NavController for navigation using NavGraph
    private lateinit var navController: NavController
    //Data binding for this activity
    lateinit var mainBinding: ActivityMainBinding


    /**
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set up View binding for main activity
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        // Set up bottom app navigation bar to access fragments.
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFrag) as NavHostFragment
        navController = navHostFragment.findNavController()

        // Set up top app bar
        val topAppBar: MaterialToolbar = mainBinding.topNavBar
        setSupportActionBar(topAppBar)
        topAppBar.setupWithNavController(navController)

        // set up bottom navigation bar:
        createBottomNavBar()

        // Initialise datastore repository, topic view model and room database for saved articles.
        dataStoreRepo = DataStoreRepo(this.applicationContext)
        topicsViewModel = TopicViewModel(dataStoreRepo)
        localNewsSource = utilsContainer.setUpDatabase(this.applicationContext) // tied to lifecycle of the application.
    }

    /**
     * Helper function to set the bottom navigation bar with the NavController.
     */
    private fun createBottomNavBar() {
        AppBarConfiguration(
            setOf(R.id.newsFragment, R.id.weatherFragment, R.id.bookmarksFragment, R.id.profileFragment)
        )
        mainBinding.bottomNavView.setupWithNavController(navController)

        // If we load the full screen article view, hide the bottom nav bar for a cleaner look.
        navController.addOnDestinationChangedListener { _, frag, _ ->
            if(frag.id == R.id.articleFragment) {
                mainBinding.bottomNavView.visibility = View.GONE
            } else {
                mainBinding.bottomNavView.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_top, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item)
    }

    //enable the up button on the nav controller.
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}