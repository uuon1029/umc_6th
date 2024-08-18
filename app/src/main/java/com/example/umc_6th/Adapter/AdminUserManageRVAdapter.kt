package com.example.umc_6th.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.bumptech.glide.Glide
import com.example.umc_6th.BookmarkRVAdapter
import com.example.umc_6th.R
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

        private var isVisible : Boolean = false
        fun bind(user: User){
            Glide.with(binding.itemManageUserProfileImgIv.context)
                .load(user.pic)
                .into(binding.itemManageUserProfileImgIv)

            binding.itemManageUserIdTv.text = user.account
            binding.itemManageUserNameTv.text = user.name
            binding.itemManageUserNicknameTv.text = user.nickName
            binding.itemManageUserDateTv.text = user.createdAt
            binding.itemManageUserReportCntTv.text = user.report.toString()
            binding.itemManageUserWarningCntTv.text = user.warn.toString()
            binding.itemManageUserSuspensionCntTv.text = user.stop.toString()


            if (user.status != null) {
                if (user.status == "SUSPENSION") {
                    binding.itemManageUserSuspensionBtnIv.visibility = View.GONE
                    binding.itemManageUserSuspensionCancelBtnIv.visibility = View.VISIBLE
                    Log.d("Check_suspension",user.status)
                } else {
                    binding.itemManageUserSuspensionBtnIv.visibility = View.VISIBLE
                    binding.itemManageUserSuspensionCancelBtnIv.visibility = View.GONE
                    Log.d("Check_suspension",user.status)
                }
            }

            binding.itemManageUserSuspensionBtnIv.setOnClickListener {
                binding.itemManageUserSuspensionBtnIv.visibility = View.GONE
                binding.itemManageUserSuspensionCancelBtnIv.visibility = View.VISIBLE
            }

            binding.itemManageUserSuspensionCancelBtnIv.setOnClickListener {
                binding.itemManageUserSuspensionBtnIv.visibility = View.VISIBLE
                binding.itemManageUserSuspensionCancelBtnIv.visibility = View.GONE
            }

            binding.root.setOnClickListener {
                if(isVisible) {
                    binding.itemManageUserOptionBoxCl.visibility = View.VISIBLE
                } else {
                    binding.itemManageUserOptionBoxCl.visibility = View.GONE
                }

                isVisible = !isVisible
            }
        }
    }

}