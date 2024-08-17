package com.example.umc_6th.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.BookmarkRVAdapter
import com.example.umc_6th.Retrofit.DataClass.Bookmark
import com.example.umc_6th.Retrofit.DataClass.User
import com.example.umc_6th.databinding.ItemBookmarkBinding
import com.example.umc_6th.databinding.ItemManageUserBinding

class AdminUserManageRVAdapter(private var usersList:ArrayList<User>): RecyclerView.Adapter<AdminUserManageRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AdminUserManageRVAdapter.ViewHolder {
        val binding : ItemManageUserBinding = ItemManageUserBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdminUserManageRVAdapter.ViewHolder, position: Int) {
        holder.bind(usersList[position])
    }

    override fun getItemCount(): Int = usersList.size

    inner class ViewHolder(val binding: ItemManageUserBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: User){
            binding.itemManageUserProfileImgIv.setImageResource(user.pic)
            binding.itemManageUserIdTv.text = user.account
            binding.itemManageUserNicknameTv.text = user.nickName
            binding.itemManageUserDateTv.text = user.createdAt
            binding.itemManageUserReportCntTv.text = user.report.toString()
            binding.itemManageUserWarningCntTv.text = user.warn.toString()
            binding.itemManageUserSuspensionCntTv.text = user.stop.toString()
        }
    }

}