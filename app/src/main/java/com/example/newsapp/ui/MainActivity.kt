package com.example.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var navControllerMain: NavController
    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set up View binding for main activity
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        // Set up bottom app navigation bar to access fragments.
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFrag) as NavHostFragment
        navControllerMain = navHostFragment.navController
        NavigationUI.setupWithNavController(mainBinding.bottomNavView, navControllerMain)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navControllerMain.navigateUp() || super.onSupportNavigateUp()
    }
}