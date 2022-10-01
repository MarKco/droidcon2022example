package com.example.droidcondemoapp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    @SuppressLint("WrongConstant")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AlertDialog.Builder(this)
            .setTitle("Permission revoked")
            .setPositiveButton("OK") { dialog, which ->

            }
            .setMessage("The latest app version doesn't longer need to check for user's location, thus the matching permission was automatically self-revoked. We care about your privacy :-) ")
            .show()

    }

}