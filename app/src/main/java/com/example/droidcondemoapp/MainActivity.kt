package com.example.droidcondemoapp

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat


class MainActivity : AppCompatActivity() {

    @SuppressLint("WrongConstant")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.request_permission).setOnClickListener {
//            requestPermissions(
//                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
//                3894729
//            )
            sendNotification(findViewById(R.id.request_permission))
        }

    }


    private fun sendNotification(view: View?) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = "DroidconDemo"
            val descriptionText = "Canale della demo del droidcon"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel("398398", name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)

            //Get an instance of NotificationManager//
            val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this)
                .setSmallIcon(androidx.appcompat.R.drawable.abc_dialog_material_background)
                .setContentTitle("My notification")
                .setChannelId(mChannel.id)
                .setContentText("Hello World!")

            notificationManager.notify(1, mBuilder.build())
        }
    }
}