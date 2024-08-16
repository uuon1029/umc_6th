package com.example.umc_6th.Activity

import android.content.pm.PackageManager
import android.net.Uri

import android.os.Bundle
import android.provider.MediaStore

import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.database.Cursor
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.umc_6th.databinding.ActivityCustomGalleryProfileBinding
import com.example.umc_6th.databinding.ItemImageBinding

import com.example.umc_6th.Retrofit.Request.PicRestoreRequest
import com.example.umc_6th.Retrofit.RetrofitClient


import android.Manifest
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.PhotoActivity
import com.example.umc_6th.R
import org.checkerframework.checker.units.qual.t
import com.example.umc_6th.ConfigFragment

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class CustomGalleryProfileActivity : AppCompatActivity() {

    private val imageList = mutableListOf<String>()
    private lateinit var binding: ActivityCustomGalleryProfileBinding
    private val selectedImages = mutableListOf<String>()
    //lateinit var accessToken: String? = MainActivity.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomGalleryProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.galleryExit.setOnClickListener {
            finish()
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_MEDIA_IMAGES), 1)
        } else {
            loadImages()
        }

        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)

        binding.buttonSelect.setOnClickListener{

            //서버에 이미지 저장
            val selectedImage = selectedImages.firstOrNull()
            if (selectedImage != null) {
                val file = File(selectedImage)
                if (file.exists()) {
                    val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                    val multipartBody = MultipartBody.Part.createFormData("request", file.name, requestBody)

                    val sp = getSharedPreferences("Auth", MODE_PRIVATE)
                    val accessToken = sp.getString("AccessToken", toString()).toString()

                    RetrofitClient.service.patchPicRestore(accessToken, multipartBody).enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful) {
                                Log.d("Retrofit", "이미지가 서버에 저장되었습니다.")
                                Toast.makeText(this@CustomGalleryProfileActivity, "이미지가 서버에 저장되었습니다.", Toast.LENGTH_SHORT).show()
                                //intent

                                // 선택한 이미지를 전달하고 액티비티 종료
                                val resultIntent = Intent().apply {
                                    putExtra("profile_image", selectedImage)
                                }
                                setResult(RESULT_OK, resultIntent)
                                finish()

                            } else {
                                Log.e("Retrofit", "이미지 저장에 실패했습니다. 응답 코드: ${response.code()}, 응답 메시지: ${response.message()}")
                                Toast.makeText(this@CustomGalleryProfileActivity, "이미지 저장에 실패했습니다.", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Log.e("Retrofit", "서버와의 통신에 실패했습니다. 오류: ${t.message}")
                            Toast.makeText(this@CustomGalleryProfileActivity, "서버와의 통신에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    Log.e("Retrofit", "파일이 존재하지 않습니다.")
                    Toast.makeText(this@CustomGalleryProfileActivity, "파일이 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "이미지를 선택하세요", Toast.LENGTH_SHORT).show()
            }


            /*

            val selectedImage = selectedImages.firstOrNull() // 선택된 이미지의 첫 번째 항목을 가져옴
            if (selectedImage != null) {
                val imageUri = Uri.parse(selectedImage)
                val intent = Intent(this, CropActivity::class.java).apply {
                    putExtra(CropActivity.EXTRA_IMAGE_URI, imageUri.toString())
                }
                startActivityForResult(intent, REQUEST_CROP_IMAGE)
            } else {
                Toast.makeText(this, "이미지를 선택하세요", Toast.LENGTH_SHORT).show()
            }

             */

        }


        val arrowImageView = binding.galleryArrowIv
        arrowImageView.setOnClickListener {
            //showAlbumList()
            toggleAlbumList()
        }




    }

    private fun getAlbumList(): List<String> {
        val albumList = mutableListOf<String>()
        val projection = arrayOf(
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.BUCKET_ID
        )
        val cursor: Cursor? = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )
        cursor?.use {
            val bucketColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
            while (it.moveToNext()) {
                val bucketName = it.getString(bucketColumn)
                if (!albumList.contains(bucketName)) {
                    albumList.add(bucketName)
                }
            }
        }
        return albumList
    }

    private fun showAlbumList() {
        val albumList = getAlbumList()
        val albumRecyclerView = findViewById<RecyclerView>(R.id.album_recycler_view)
        albumRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = AlbumAdapter(albumList)
        albumRecyclerView.adapter = adapter
    }

    private fun toggleAlbumList() {
        val albumRecyclerView = findViewById<RecyclerView>(R.id.album_recycler_view)
        if (albumRecyclerView.visibility == View.VISIBLE) {
            binding.galleryArrowIv.setImageResource(R.drawable.ic_down_arrow)
            albumRecyclerView.visibility = View.GONE

        } else {
            binding.galleryArrowIv.setImageResource(R.drawable.ic_up_arrow)
            albumRecyclerView.visibility = View.VISIBLE
            showAlbumList()
        }
    }




    inner class AlbumAdapter(private val albumList: List<String>) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.album_item, parent, false)
            return AlbumViewHolder(view)
        }

        override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
            holder.albumNameTextView.text = albumList[position]
        }

        override fun getItemCount(): Int {
            return albumList.size
        }

        inner class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val albumNameTextView: TextView = itemView.findViewById(R.id.album_name_text_view)
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadImages()
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadImages() {
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.MediaColumns.DATA)
        val cursor: Cursor? = contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            val columnIndexData = it.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            while (it.moveToNext()) {
                imageList.add(it.getString(columnIndexData))
            }
        }

        val adapter = GalleryAdapter(imageList) { selectedImages ->

        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }



    inner class GalleryAdapter(
        private val items: List<String>,
        private val onItemClick: (List<String>) -> Unit
    ) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

        private val maxChecked = 1

        inner class ViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {

            init {
                binding.root.setOnClickListener {
                    binding.checkBox.performClick()
                }
            }

            fun bind(imagePath: String) {
                Glide.with(binding.iv.context)
                    .load(imagePath)
                    .into(binding.iv)

                binding.checkBox.setOnCheckedChangeListener(null) // 리스너 제거
                binding.checkBox.isChecked = selectedImages.contains(imagePath)

                binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        if (selectedImages.size >= maxChecked) {
                            binding.checkBox.isChecked = false
                            Toast.makeText(binding.root.context, "크롭창으로 넘어갑시다", Toast.LENGTH_SHORT).show()
                        } else {
                            selectedImages.add(imagePath)
                            onItemClick(selectedImages)
                        }
                    } else {
                        selectedImages.remove(imagePath)
                        onItemClick(selectedImages)
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(items[position])
        }

        override fun getItemCount(): Int = items.size

    }
    companion object {
        private const val REQUEST_CROP_IMAGE = 2
    }
}