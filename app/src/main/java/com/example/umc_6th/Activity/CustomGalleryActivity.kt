package com.example.umc_6th.Activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_6th.PhotoActivity
import com.example.umc_6th.R
import com.example.umc_6th.databinding.ActivityCustomGalleryBinding
import com.example.umc_6th.databinding.ItemImageBinding
import com.example.umc_6th.databinding.AlbumItemBinding

class CustomGalleryActivity : AppCompatActivity() {

    private val imageList = mutableListOf<String>()
    private lateinit var binding: ActivityCustomGalleryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        checkPermissionsAndLoadImages()

        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerView.adapter = GalleryAdapter(imageList, ::updateSelectedImages)
        binding.albumRecyclerView.visibility = View.GONE
    }

    private fun setupUI() {
        binding.galleryExit.setOnClickListener { finish() }

        binding.photoSelectedContainer.setOnClickListener { openPhotoActivity() }

        binding.galleryArrowIv.setOnClickListener { toggleAlbumList() }

        binding.photoContainerDelete1.setOnClickListener { removeSelectedImage(0) }
        binding.photoContainerDelete2.setOnClickListener { removeSelectedImage(1) }
        binding.photoContainerDelete3.setOnClickListener { removeSelectedImage(2) }

        binding.buttonSelect.setOnClickListener {
            handleImageSelection()
        }
    }

    private fun checkPermissionsAndLoadImages() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_MEDIA_IMAGES), 1)
        } else {
            loadImages()
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
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun handleImageSelection() {
        val adapter = binding.recyclerView.adapter as GalleryAdapter
        val selectedImages = adapter.getSelectedImages()
        if (selectedImages.isNotEmpty()) {
            val intent = Intent().apply {
                putStringArrayListExtra("selectedImages", ArrayList(selectedImages))
            }
            setResult(RESULT_OK, intent)
            //Toast.makeText(this, "이미지 보여주기.", Toast.LENGTH_SHORT).show()
            Log.d("커스텀액티비티", "이미지 보냄")
            finish()
        } else {
            Toast.makeText(this, "이미지를 선택하세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateAlbumName(albumName: String) {
        binding.albumListName.text = albumName
    }

    private fun getAlbumList(): List<String> {
        val albumSet = mutableSetOf<String>()
        val projection = arrayOf(
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        )

        val cursor: Cursor? = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )

        cursor?.use {
            val bucketColumn = it.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
            if (bucketColumn != -1) {
                while (it.moveToNext()) {
                    val bucketName = it.getString(bucketColumn)

                    if (bucketName.isNullOrEmpty()) {
                        Log.e("CustomGalleryActivity", "Null or empty bucketName found")
                    } else {
                        if (!albumSet.add(bucketName)) {
                            //Log.e("CustomGalleryActivity", "Duplicate bucketName found: $bucketName")
                        }
                    }
                }
            } else {
                Log.e("CustomGalleryActivity", "Column BUCKET_DISPLAY_NAME not found")
            }
        } ?: run {
            Log.e("CustomGalleryActivity", "Cursor is null")
        }

        val albumList = albumSet.toList()
        Log.d("CustomGalleryActivity", "Album list size: ${albumList.size}")
        return albumList
    }


    private fun showAlbumList() {
        try {
            val albumList = getAlbumList()
            Log.d("CustomGalleryActivity", "Album list size: ${albumList.size}")
            // RecyclerView를 안전하게 설정합니다.
            binding.albumRecyclerView?.let { recyclerView ->
                recyclerView.layoutManager = LinearLayoutManager(this@CustomGalleryActivity)
                recyclerView.adapter = AlbumAdapter(albumList)
                {albumName ->
                    updateAlbumName(albumName)
                    loadImagesFromAlbum(albumName) }
                recyclerView.visibility = View.VISIBLE // RecyclerView가 보이도록 설정합니다.
            } ?: run {
                Log.e("CustomGalleryActivity", "RecyclerView (albumRecyclerView) is null in showAlbumList")
            }
        } catch (e: Exception) {
            Log.e("CustomGalleryActivity", "Error showing album list", e)
        }
    }

    private fun toggleAlbumList() {
        with(binding.albumRecyclerView) {
            if (visibility == View.VISIBLE) {
                binding.galleryArrowIv.setImageResource(R.drawable.ic_down_arrow)
                visibility = View.GONE
            } else {
                binding.galleryArrowIv.setImageResource(R.drawable.ic_up_arrow)
                visibility = View.VISIBLE
                showAlbumList()
            }
        }
    }

    private fun loadImagesFromAlbum(albumName: String) {
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.MediaColumns.DATA)
        val selection = "${MediaStore.Images.Media.BUCKET_DISPLAY_NAME} = ?"
        val selectionArgs = arrayOf(albumName)
        val cursor: Cursor? = contentResolver.query(uri, projection, selection, selectionArgs, null)

        imageList.clear() // 기존 이미지 리스트를 초기화합니다.
        cursor?.use {
            val columnIndexData = it.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            while (it.moveToNext()) {
                imageList.add(it.getString(columnIndexData))
            }
        } ?: run {
            // cursor가 null인 경우를 처리
            Log.e("CustomGalleryActivity", "Failed to retrieve images for album: $albumName")
        }

        // RecyclerView를 업데이트합니다.
        binding.recyclerView.adapter?.notifyDataSetChanged()
        // 앨범 목록을 숨깁니다.
        toggleAlbumList()
    }

    private fun updateSelectedImages(selectedImages: List<String>) {
        val imageViews = listOf(binding.photoSeleted1, binding.photoSeleted2, binding.photoSeleted3)
        val deleteButtons = listOf(binding.photoContainerDelete1, binding.photoContainerDelete2, binding.photoContainerDelete3)

        selectedImages.forEachIndexed { index, imagePath ->
            if (index < imageViews.size) {
                Glide.with(this).load(imagePath).into(imageViews[index])
                imageViews[index].visibility = View.VISIBLE
                deleteButtons[index].visibility = View.VISIBLE
            }
        }

        for (i in selectedImages.size until imageViews.size) {
            imageViews[i].visibility = View.INVISIBLE
            deleteButtons[i].visibility = View.INVISIBLE
        }

        binding.photoSelectedContainer.visibility = if (selectedImages.isEmpty()) View.INVISIBLE else View.VISIBLE
    }

    private fun removeSelectedImage(index: Int) {
        val adapter = binding.recyclerView.adapter as GalleryAdapter
        val selectedImages = adapter.getSelectedImages().toMutableList()
        if (index < selectedImages.size) {
            selectedImages.removeAt(index)
            adapter.updateSelectedImages(selectedImages)
            updateSelectedImages(selectedImages)
        }
    }

    private fun openPhotoActivity() {
        val adapter = binding.recyclerView.adapter as GalleryAdapter
        val selectedImages = adapter.getSelectedImages()
        if (selectedImages.isNotEmpty()) {
            val intent = Intent(this, PhotoActivity::class.java).apply {
                putStringArrayListExtra("photoUris", ArrayList(selectedImages))
            }
            startActivity(intent)
        }
    }

    inner class AlbumAdapter(
        private val albumList: List<String>,
        private val onAlbumClick: (String) -> Unit
    ) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

        inner class AlbumViewHolder(private val binding: AlbumItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(albumName: String) {
                binding.albumNameTextView.text = albumName
                itemView.setOnClickListener {
                    onAlbumClick(albumName)
                    //binding.albumListName.text = albumName
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
            val Binding = AlbumItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return AlbumViewHolder(Binding)
        }

        override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
            holder.bind(albumList[position])
        }

        override fun getItemCount(): Int = albumList.size
    }

    inner class GalleryAdapter(
        private val items: List<String>,
        private val onItemClick: (List<String>) -> Unit
    ) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

        private val maxChecked = 3
        private val selectedImages = mutableListOf<String>()

        inner class ViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {

            init {
                binding.root.setOnClickListener { binding.checkBox.performClick() }
            }

            fun bind(imagePath: String) {
                Glide.with(binding.iv.context).load(imagePath).into(binding.iv)

                binding.checkBox.setOnCheckedChangeListener(null)
                binding.checkBox.isChecked = selectedImages.contains(imagePath)

                binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        if (selectedImages.size >= maxChecked) {
                            binding.checkBox.isChecked = false
                            Toast.makeText(binding.root.context, "최대 세 개까지 가능합니다.", Toast.LENGTH_SHORT).show()
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

        fun getSelectedImages(): List<String> = selectedImages

        fun updateSelectedImages(images: List<String>) {
            selectedImages.clear()
            selectedImages.addAll(images)
            notifyDataSetChanged()
        }
    }
}