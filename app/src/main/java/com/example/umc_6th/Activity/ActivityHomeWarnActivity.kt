package com.example.umc_6th

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.umc_6th.databinding.ActivityAdminWarnReasonBinding
import com.example.umc_6th.databinding.ActivityPhotoBinding
import java.io.File
import java.net.URI


class ActivityHomeWarnActivity: AppCompatActivity() {

    lateinit var binding: ActivityAdminWarnReasonBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminWarnReasonBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}

