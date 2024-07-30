package com.example.umc_6th

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
import com.example.umc_6th.databinding.ActivityCustomGalleryBinding
import com.example.umc_6th.databinding.ItemImageBinding

import android.Manifest
import android.view.View

/*

class CustomGalleryActivity : AppCompatActivity() {

    private val imageList = mutableListOf<String>()
    private lateinit var binding: ActivityCustomGalleryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomGalleryBinding.inflate(layoutInflater)
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
        binding.recyclerView.adapter = GalleryAdapter(imageList) { updateSelectedImages(it) }

        binding.photoContainerDelete1.setOnClickListener { removeSelectedImage(0) }
        binding.photoContainerDelete2.setOnClickListener { removeSelectedImage(1) }
        binding.photoContainerDelete3.setOnClickListener { removeSelectedImage(2) }
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
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun updateSelectedImages(selectedImages: List<String>) {
        val imageViews = listOf(binding.photoSeleted1, binding.photoSeleted2, binding.photoSeleted3)
        val deleteButtons = listOf(binding.photoContainerDelete1, binding.photoContainerDelete2, binding.photoContainerDelete3)

        selectedImages.forEachIndexed { index, imagePath ->
            if (index < imageViews.size) {
                Glide.with(this)
                    .load(imagePath)
                    .into(imageViews[index])
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
        val selectedImages = (binding.recyclerView.adapter as GalleryAdapter).getSelectedImages().toMutableList()
        if (index < selectedImages.size) {
            selectedImages.removeAt(index)
            (binding.recyclerView.adapter as GalleryAdapter).updateSelectedImages(selectedImages)
            updateSelectedImages(selectedImages)
        }
    }

    inner class GalleryAdapter(
        private val items: List<String>,
        private val onItemClick: (List<String>) -> Unit // 콜백을 추가하여 선택된 이미지의 리스트를 전달
    ) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

        private val maxChecked = 3
        private var checkedCount = 0
        private val selectedImages = mutableListOf<String>()

        inner class ViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {

            init {
                // 아이템 클릭 시 체크박스 상태 변경
                binding.root.setOnClickListener {
                    binding.checkBox.performClick()
                }
            }

            fun bind(imagePath: String) {
                Glide.with(binding.iv.context)
                    .load(imagePath)
                    .into(binding.iv)

                binding.checkBox.setOnCheckedChangeListener { check, isChecked ->
                    if (isChecked) {
                        if (checkedCount >= maxChecked) {
                            check.isChecked = false
                            Toast.makeText(binding.root.context, "최대 세 개까지 가능합니다.", Toast.LENGTH_SHORT).show()
                        } else {
                            checkedCount++
                            selectedImages.add(imagePath)
                            onItemClick(selectedImages)
                        }
                    } else {
                        checkedCount--
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

        override fun getItemCount(): Int {
            return items.size
        }
        fun getSelectedImages(): List<String> = selectedImages

        fun updateSelectedImages(images: List<String>) {
            selectedImages.clear()
            selectedImages.addAll(images)
            checkedCount = selectedImages.size
            notifyDataSetChanged()
        }
    }
}


 */

class CustomGalleryActivity : AppCompatActivity() {

    private val imageList = mutableListOf<String>()
    private lateinit var binding: ActivityCustomGalleryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomGalleryBinding.inflate(layoutInflater)
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
        binding.recyclerView.adapter = GalleryAdapter(imageList) { updateSelectedImages(it) }

        binding.photoContainerDelete1.setOnClickListener { removeSelectedImage(0) }
        binding.photoContainerDelete2.setOnClickListener { removeSelectedImage(1) }
        binding.photoContainerDelete3.setOnClickListener { removeSelectedImage(2) }
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
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun updateSelectedImages(selectedImages: List<String>) {
        val imageViews = listOf(binding.photoSeleted1, binding.photoSeleted2, binding.photoSeleted3)
        val deleteButtons = listOf(binding.photoContainerDelete1, binding.photoContainerDelete2, binding.photoContainerDelete3)

        selectedImages.forEachIndexed { index, imagePath ->
            if (index < imageViews.size) {
                Glide.with(this)
                    .load(imagePath)
                    .into(imageViews[index])
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

    inner class GalleryAdapter(
        private val items: List<String>,
        private val onItemClick: (List<String>) -> Unit
    ) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

        private val maxChecked = 3
        private val selectedImages = mutableListOf<String>()

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