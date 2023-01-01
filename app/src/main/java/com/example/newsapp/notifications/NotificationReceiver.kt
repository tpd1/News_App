package com.example.newsapp.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.newsapp.ui.MainActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Broadcast receiver that posts a notification when alerted by the AlarmManager.
 */
class NotificationReceiver : BroadcastReceiver() {

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context, intent: Intent) {
        val notificationController = (context as MainActivity).notificationController
        GlobalScope.launch {
            notificationController.getArticle()
        }
    }

}