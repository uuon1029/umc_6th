package com.example.umc_6th.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Retrofit.DataClass.ProfileBoard
import com.example.umc_6th.databinding.ItemProfileBoardBinding

class ProfileBoardRVAdapter : RecyclerView.Adapter<ProfileBoardRVAdapter.ViewHolder>() {

    private var boardList = ArrayList<ProfileBoard>()
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ProfileBoardRVAdapter.ViewHolder {
        val binding : ItemProfileBoardBinding = ItemProfileBoardBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = boardList.size

    override fun onBindViewHolder(holder: ProfileBoardRVAdapter.ViewHolder, position: Int) {
        holder.bind(boardList[position])
    }

    inner class ViewHolder(val binding : ItemProfileBoardBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item:ProfileBoard){
            binding.itemProfileBoardTitleTv.text = item.title
            binding.itemProfileBoardBodyTv.text = item.

        }
    }

}