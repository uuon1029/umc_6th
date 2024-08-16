package com.example.umc_6th

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Data.ProfileBoard
import com.example.umc_6th.Retrofit.DataClass.PinContent
import com.example.umc_6th.databinding.ItemProfileBoardBinding
import com.example.umc_6th.databinding.ItemProfileCommentBinding

class AdminProfileCommentRVAdapter(var adminprofilecommenetlist: ArrayList<PinContent>) : RecyclerView.Adapter<AdminProfileCommentRVAdapter.ViewHolder>(){

    fun interface MyItemClickListener{
        fun onItemClick(item: PinContent)

    }
    private lateinit var myItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        myItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProfileCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    override fun getItemCount(): Int = adminprofilecommenetlist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(adminprofilecommenetlist[position])
        holder.itemView.setOnClickListener {
            myItemClickListener.onItemClick(adminprofilecommenetlist[position])
        }
    }

    inner class ViewHolder(private val binding: ItemProfileCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PinContent) {
            binding.itemProfileCommentTitleTv.text = item.comment
            binding.itemProfileCommentDateTv.text = item.createdAt
        }
    }
}