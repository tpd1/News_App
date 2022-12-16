package com.example.newsapp.model

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//Needed for dagger / hilt set up. Wrapper for other classes.
@HiltAndroidApp
class NewsApplication: Application() {
}