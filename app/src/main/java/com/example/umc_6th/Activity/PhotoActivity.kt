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
import com.example.umc_6th.databinding.ActivityPhotoBinding
import java.io.File
import java.net.URI


class PhotoActivity: AppCompatActivity() {

    lateinit var binding: ActivityPhotoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        //사진 uri 전달 받음.
        val uriString = intent.getStringExtra("photoUri")
        val uri = uriString?.let { Uri.parse(it) }
        if (uri != null) {
            binding.photo.setImageURI(uri)
        }

         */

        val photoUris = intent.getStringArrayListExtra("photoUris")?.map { Uri.parse(it) } ?: listOf()
        photoUris.forEach { uri ->
            Log.d("PhotoActivity", "Parsed URI: $uri")
            val file = File(uri.path)
            Log.d("PhotoActivity", "File exists: ${file.exists()}")
        }

        val adapter = PhotoPagerAdapter(photoUris)
        binding.viewPager.adapter = adapter
        binding.indicator.setupWithViewPager(binding.viewPager, true)

        binding.photoExit.setOnClickListener {
            finish()
        }
    }

    inner class PhotoPagerAdapter(private val photos: List<Uri>) : androidx.viewpager.widget.PagerAdapter() {

        override fun getCount(): Int = photos.size

        override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val imageView = ImageView(container.context).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
            val uri = photos[position]
            Log.d("PhotoPagerAdapter", "Loading image at position: $position, Uri: $uri")
            Glide.with(container.context)
                //.load(uri)
                .load(File(uri.path)) // 파일 경로를 직접 전달
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.e("PhotoPagerAdapter", "Glide load failed", e)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .into(imageView)

            container.addView(imageView)
            return imageView
        }


        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as ImageView)
        }
    }
}

