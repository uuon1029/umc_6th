package com.example.umc_6th

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageView


/*
class ImageAdapter(
    private val context: Context,
    private val imageUris: List<Uri>,
    private val selectedImages: MutableList<Uri>
) : BaseAdapter() {

    override fun getCount(): Int = imageUris.size

    override fun getItem(position: Int): Any = imageUris[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_image, parent, false)
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)

        val uri = imageUris[position]
        Glide.with(context).load(uri).into(imageView)

        checkBox.isChecked = selectedImages.contains(uri)

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedImages.add(uri)
            } else {
                selectedImages.remove(uri)
            }
        }

        return view
    }
}

 */