package com.example.umc_6th

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivityPhotoBinding


class PhotoActivity: AppCompatActivity() {

    lateinit var binding: ActivityPhotoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //사진 uri 전달 받음.
        val uriString = intent.getStringExtra("photoUri")
        val uri = uriString?.let { Uri.parse(it) }
        if (uri != null) {
            binding.photo.setImageURI(uri)
        }

        binding.photoExit.setOnClickListener {
            finish()
        }
    }
}