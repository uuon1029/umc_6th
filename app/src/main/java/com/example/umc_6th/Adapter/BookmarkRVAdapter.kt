package com.example.umc_6th

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Retrofit.DataClass.Bookmark
import com.example.umc_6th.databinding.ItemBookmarkBinding

class BookmarkRVAdapter(private var bookmarkList:ArrayList<Bookmark>):RecyclerView.Adapter<BookmarkRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BookmarkRVAdapter.ViewHolder {
        val binding : ItemBookmarkBinding = ItemBookmarkBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkRVAdapter.ViewHolder, position: Int) {
        holder.bind(bookmarkList[position])
    }

    override fun getItemCount(): Int = bookmarkList.size

    inner class ViewHolder(val binding: ItemBookmarkBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(bookmark: Bookmark){
            binding.itemBookmarkTitleTv.text = bookmark.tag
            binding.itemBookmarkContentTv.text = bookmark.problem
        }
    }

}