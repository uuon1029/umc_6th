package com.example.umc_6th

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Data.ProfileBoard
import com.example.umc_6th.Retrofit.DataClass.Content
import com.example.umc_6th.databinding.ItemProfileBoardBinding

class AdminProfileBoardRVAdapter(var adminprofileboardlist: ArrayList<Content>) : RecyclerView.Adapter<AdminProfileBoardRVAdapter.ViewHolder>(){

    fun interface MyItemClickListener{
        fun onItemClick(item: Content)

    }
    private lateinit var myItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        myItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProfileBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    override fun getItemCount(): Int = adminprofileboardlist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(adminprofileboardlist[position])
        holder.itemView.setOnClickListener {
            myItemClickListener.onItemClick(adminprofileboardlist[position])
        }
    }

    inner class ViewHolder(private val binding: ItemProfileBoardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Content) {
            binding.itemProfileBoardTitleTv.text = item.title
            binding.itemProfileBoardBodyTv.text = item.content
            binding.itemProfileBoardDateTv.text = item.createdAt
        }
    }
}