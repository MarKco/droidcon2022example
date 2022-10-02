package com.example.droidcondemoapp

import android.annotation.SuppressLint
import android.app.LocaleManager
import android.app.StatusBarManager
import android.content.ComponentName
import android.content.Context
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {

    private var localeManager: LocaleManager? = null

    // Registers a photo picker activity launcher in single-select mode.
    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    @SuppressLint("WrongConstant")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button_for_tile).setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val statusBarManager =
                    getSystemService(Context.STATUS_BAR_SERVICE) as StatusBarManager
                /**
                 * Important: Change the package name accordingly to your own package name!
                 */
                statusBarManager.requestAddTileService(
                    ComponentName(
                        "com.example.droidcondemoapp",
                        "com.example.droidcondemoapp.MySampleTileService",
                    ),
                    "Droidcon Example App",
                    Icon.createWithResource(this, R.drawable.ic_launcher_foreground),
                    {},
                    {}
                )
            } else {
                Toast.makeText(
                    this,
                    "`requestAddTileService` can only be called in Android 13/Tiramisu.",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            localeManager =
                getSystemService(Context.LOCALE_SERVICE) as LocaleManager
        }

        findViewById<Button>(R.id.button_lang_en).setOnClickListener {
            localeManager?.applicationLocales = LocaleList(Locale.forLanguageTag("en"))
        }
        findViewById<Button>(R.id.button_lang_it).setOnClickListener {
            localeManager?.applicationLocales = LocaleList(Locale.forLanguageTag("it"))
        }
        findViewById<Button>(R.id.button_lang_reset).setOnClickListener {
            localeManager?.applicationLocales = LocaleList.getEmptyLocaleList()
        }

    }

    override fun onResume() {
        super.onResume()
        Log.d("DroidconDemoApp", "onResume")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val language = when (localeManager?.applicationLocales?.toLanguageTags()) {
                "en" -> "English"
                "it" -> "Italian"
                else -> "Not Set"
            }
            Log.wtf("LANG", localeManager?.applicationLocales?.toLanguageTags())
            findViewById<TextView>(R.id.language_textview).text =
                "Current In-App Language: $language"
        }
    }
}