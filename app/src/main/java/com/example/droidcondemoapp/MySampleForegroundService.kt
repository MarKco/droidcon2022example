package com.example.droidcondemoapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat


class MySampleForegroundService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val channel1 = NotificationChannel(
            "CHANNEL_1_ID",
            "Channel 1",
            NotificationManager.IMPORTANCE_HIGH
        )
        channel1.description = "This is channel 1"

        val channel2 = NotificationChannel(
            "CHANNEL_2_ID",
            "Channel 2",
            NotificationManager.IMPORTANCE_LOW
        )
        channel2.description = "This is channel 2"

        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel1)

        val input = intent?.getStringExtra("droidconTest")
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
// 1
        val notification = NotificationCompat.Builder(this, channel1.id)
            .setContentTitle("notificationTitle")
            .setContentText(input)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1, notification)

        Log.d("DroidconService", "Service bound")
// 2
        return START_NOT_STICKY
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        Log.d("DroidconService", "Service started")
    }

    override fun onDestroy() {
        Log.d("DroidconService", "Service destroyed")
        super.onDestroy()
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("DroidconService", "Service created")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("DroidconService", "Service unbound")
        return super.onUnbind(intent)
    }

}