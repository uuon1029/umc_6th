package com.example.umc_6th.Adapter

import com.example.umc_6th.Retrofit.DataClass.AdminUserProfileBoard
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.databinding.ItemAdminUserProfileBinding

class AdminUserProfileBoardRVAdapter(var adminuserprofilelist: ArrayList<AdminUserProfileBoard>) : RecyclerView.Adapter<AdminUserProfileBoardRVAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAdminUserProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    override fun getItemCount(): Int = adminuserprofilelist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(adminuserprofilelist[position])
    }

    inner class ViewHolder(private val binding: ItemAdminUserProfileBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(adminUserProfile: AdminUserProfileBoard) {
            binding.itemHomeBoardBodyTv.text = adminUserProfile.title
        }
    }
}