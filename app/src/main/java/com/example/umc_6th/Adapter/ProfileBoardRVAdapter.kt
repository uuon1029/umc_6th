package com.example.umc_6th.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Retrofit.DataClass.Content
import com.example.umc_6th.Retrofit.DataClass.ProfileBoard
import com.example.umc_6th.databinding.ItemProfileBoardBinding

class ProfileBoardRVAdapter : RecyclerView.Adapter<ProfileBoardRVAdapter.ViewHolder>() {

    var boardList = ArrayList<Content>()
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ProfileBoardRVAdapter.ViewHolder {
        val binding : ItemProfileBoardBinding = ItemProfileBoardBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = boardList.size

    override fun onBindViewHolder(holder: ProfileBoardRVAdapter.ViewHolder, position: Int) {
        holder.bind(boardList[position])
    }

    inner class ViewHolder(val binding : ItemProfileBoardBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item:Content){
            binding.itemProfileBoardTitleTv.text = item.title
            binding.itemProfileBoardBodyTv.text = item.content
            binding.itemProfileBoardDateTv.text = (item.createdAt.substring(5,7)+"."
                    +item.createdAt.substring(8,10))
        }
    }

}