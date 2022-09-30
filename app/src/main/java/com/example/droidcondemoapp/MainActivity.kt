package com.example.droidcondemoapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.StatusBarManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.ext.SdkExtensions.getExtensionVersion
import android.provider.MediaStore
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout


class MainActivity : AppCompatActivity() {

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

        findViewById<ConstraintLayout>(R.id.background_layout).setOnClickListener {
            startPhotoPickerOrWhatev()
        }

        findViewById<Button>(R.id.button_for_tile).setOnClickListener {

            requestPermissions(
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                3894729
            )

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

        val intentForForegroundService = Intent(this, MySampleForegroundService::class.java)
        startService(intentForForegroundService)


    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun startPhotoPickerOrWhatev() {
        if (isPhotoPickerAvailable()) {
            // Include only one of the following calls to launch(), depending on the types
            // of media that you want to allow the user to choose from.

            // Launch the photo picker and allow the user to choose images and videos.
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        } else {
            val GALLERY = 129384
            Toast.makeText(this@MainActivity, "Missing Photo Picker!", Toast.LENGTH_SHORT).show()
            intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, GALLERY)
        }
    }

    // onActivityResult() handles callbacks from the photo picker.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            // Handle error
            return
        }
        // Get photo picker response for single select.
        val currentUri: Uri = data?.data!!
        findViewById<ImageView>(R.id.picture).setImageURI(currentUri)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        requestPermissions(
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            3894729
        )

        return super.onCreateView(name, context, attrs)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun isPhotoPickerAvailable(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            true
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getExtensionVersion(Build.VERSION_CODES.R) >= 2
        } else {
            false
        }
    }
}