package com.example.umc_6th

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Data.ProfileBoard
import com.example.umc_6th.databinding.ItemProfileBoardBinding

class AdminProfileBoardRVAdapter(var adminprofileboardlist: ArrayList<ProfileBoard>) : RecyclerView.Adapter<AdminProfileBoardRVAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProfileBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    override fun getItemCount(): Int = adminprofileboardlist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(adminprofileboardlist[position])
    }

    inner class ViewHolder(private val binding: ItemProfileBoardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ProfileBoard: ProfileBoard) {
            binding.itemProfileBoardTitleTv.text = ProfileBoard.admin_profile_board_title
            binding.itemProfileBoardBodyTv.text = ProfileBoard.admin_profile_board_body
            binding.itemProfileBoardDateTv.text = ProfileBoard.admin_profile_board_date
        }
    }
}