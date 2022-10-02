package com.example.droidcondemoapp

import android.os.Build
import android.service.quicksettings.TileService
import android.widget.Toast
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.N)
class MySampleTileService : TileService() {

    override fun onTileAdded() {
        Toast.makeText(baseContext, "Ciao a tutti", Toast.LENGTH_SHORT).show()
    }

    override fun onClick() {
        super.onClick()
        Toast.makeText(applicationContext, "Ciao a tutti", Toast.LENGTH_SHORT).show()
    }

}