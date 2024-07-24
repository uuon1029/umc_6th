package com.example.umc_6th

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import com.google.firestore.v1.Cursor

class CustomGalleryActivity : AppCompatActivity() {

    private val REQUEST_PERMISSION_CODE = 100

    private lateinit var gridView: GridView
    //private lateinit var buttonSelect: Button
    //private lateinit var imageAdapter: ImageAdapter
    private var selectedImages = mutableListOf<Uri>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_gallery)
        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_PERMISSION_CODE)
            } else {
                loadImages()
            }
        } else {
            loadImages()
        }


        buttonSelect.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putParcelableArrayListExtra("selectedImages", ArrayList(selectedImages))
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }


        gridView = findViewById(R.id.gridView)
        buttonSelect = findViewById(R.id.buttonSelect)

        loadImages()

        buttonSelect.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putParcelableArrayListExtra("selectedImages", ArrayList(selectedImages))
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }


    private fun loadImages() {
        val projection = arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null)
        val imageUris = mutableListOf<Uri>()

        cursor?.use {
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id.toString())
                imageUris.add(uri)
            }
        }

        imageAdapter = ImageAdapter(this, imageUris, selectedImages)
        gridView.adapter = imageAdapter
    }





    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadImages()
            } else {
                // 권한 거부 처리
            }
        }

         */
    }




}
