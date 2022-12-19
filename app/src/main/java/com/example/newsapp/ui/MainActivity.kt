package com.example.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.R
import com.example.newsapp.UtilsContainer
import com.example.newsapp.data.DataStoreRepo
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.model.TopicViewModel
import com.google.android.material.appbar.MaterialToolbar


// Main activity that sets up the view model for the news fragment to use, along with a shared
// article repository and database.

class MainActivity : AppCompatActivity() {
    // Create class to handle storing and loading of user settings (e.g. topic selection)
    lateinit var dataStoreRepo: DataStoreRepo
    lateinit var topicsViewModel: TopicViewModel

    // NavController for navigation using NavGraph
    private lateinit var navController: NavController

    //Data binding for this activity
    private lateinit var mainBinding: ActivityMainBinding

    // Utils container creates one instance of dependencies for other classes across app.
    val utilsContainer = UtilsContainer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set up View binding for main activity
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        // Set up bottom app navigation bar to access fragments.
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFrag) as NavHostFragment
        navController = navHostFragment.findNavController()

        // Set up bottom navigation bar
        val topAppBar: MaterialToolbar = mainBinding.topNavBar
        setSupportActionBar(topAppBar)
        topAppBar.setupWithNavController(navController)


        AppBarConfiguration(
            setOf(R.id.newsFragment, R.id.weatherFragment, R.id.bookmarksFragment, R.id.profileFragment)
        )
        mainBinding.bottomNavView.setupWithNavController(navController)

        // Initialise datastore repository for topics here, as we need the values for the tab layout.
        dataStoreRepo = DataStoreRepo(this)
        topicsViewModel = TopicViewModel(dataStoreRepo)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_top, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}