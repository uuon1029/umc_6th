package com.example.umc_6th.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_6th.Data.AdminReport
import com.example.umc_6th.Data.AdminReportComment
import com.example.umc_6th.Retrofit.DataClass.Complaint
import com.example.umc_6th.databinding.ItemAdminReportBoardAnsBinding

class AdminReportCommentRVAdapter(var adminReportCommentList: ArrayList<Complaint>) : RecyclerView.Adapter<AdminReportCommentRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemAdminReportBoardAnsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = adminReportCommentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(adminReportCommentList[position])
    }

    inner class ViewHolder(private val binding: ItemAdminReportBoardAnsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(adminReportComment: Complaint) {
            Glide.with(binding.itemAdminQuestBoardAnsProfileIv.context)
                .load(adminReportComment.userPic)
                .into(binding.itemAdminQuestBoardAnsProfileIv)
            binding.itemAdminQuestBoardAnsNameTv.text = adminReportComment.nickname
            binding.itemAdminQuestBoardAnsBodyTv.text = adminReportComment.complaintContent
            binding.itemAdminQuestBoardAnsDateTv.text = adminReportComment.createdAt
        }
    }
}
