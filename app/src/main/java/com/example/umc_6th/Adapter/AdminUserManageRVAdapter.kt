package com.example.umc_6th

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.databinding.ItemAdminUserManageBinding
import com.example.umc_6th.databinding.ItemConfigNoticeBinding

class AdminUserManageRVAdapter(var adminusermanageList : ArrayList<AdminUserManage>) : RecyclerView.Adapter<AdminUserManageRVAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemAdminUserManageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(adminUserManage: AdminUserManage) {
            binding.adminUserManageProfileIv.setImageResource(adminUserManage.admin_user_manage_img)
            binding.adminUserManageIdTv.text = adminUserManage.admin_user_manage_id
            binding.adminUserManageNicknameTv.text = adminUserManage.admin_user_manage_nickname
            binding.adminUserManageDateTv.text = adminUserManage.admin_user_manage_date
            binding.adminUserManageReportTv.text = adminUserManage.admin_user_manage_report
            binding.adminUserManageWarningTv.text = adminUserManage.admin_user_manage_warning
            binding.adminUserManageHaltTv.text = adminUserManage.admin_user_manage_halt
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAdminUserManageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }
    override fun getItemCount(): Int  = adminusermanageList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(adminusermanageList[position])
    }

}