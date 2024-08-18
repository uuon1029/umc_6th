package com.example.umc_6th

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Retrofit.DataClass.Bookmark
import com.example.umc_6th.databinding.ItemBookmarkBinding

class BookmarkRVAdapter(private var bookmarkList:ArrayList<Bookmark>):RecyclerView.Adapter<BookmarkRVAdapter.ViewHolder>() {

    interface MyOnClickListener {
        fun itemClick(item: Bookmark)
        fun unBookmark(id:Int)
    }

    private lateinit var myOnClickListener: MyOnClickListener

    fun setClickListener(onClickListener: MyOnClickListener){
        myOnClickListener = onClickListener
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BookmarkRVAdapter.ViewHolder {
        val binding : ItemBookmarkBinding = ItemBookmarkBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkRVAdapter.ViewHolder, position: Int) {
        holder.bind(bookmarkList[position])
        holder.itemView.setOnClickListener {
            myOnClickListener.itemClick(bookmarkList[position])
        }
        holder.binding.itemBookmarkStarIv.setOnClickListener {
            myOnClickListener.unBookmark(position+1)
        }
    }

    override fun getItemCount(): Int = bookmarkList.size

    inner class ViewHolder(val binding: ItemBookmarkBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(bookmark: Bookmark){
            binding.itemBookmarkTitleTv.text =
                if(bookmark.tag.length < 9){bookmark.tag} else{bookmark.tag.substring(0,9)+"..."}
            binding.itemBookmarkContentTv.text =
                if(bookmark.problem.length < 35){bookmark.problem} else{bookmark.problem.substring(0,35)+"..."}
        }
    }

}