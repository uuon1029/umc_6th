package com.example.umc_6th.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Data.AdminReportComment
import com.example.umc_6th.databinding.ItemAdminReportBoardAnsBinding

class AdminReportCommentRVAdapter(var adminReportCommentList: ArrayList<AdminReportComment>) : RecyclerView.Adapter<AdminReportCommentRVAdapter.ViewHolder>() {

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

        fun bind(adminReportComment: AdminReportComment) {
            binding.itemAdminQuestBoardAnsProfileIv.setImageResource(adminReportComment.admin_report_comment_img!!)
            binding.itemAdminQuestBoardAnsNameTv.text = adminReportComment.admin_report_comment_name
            binding.itemAdminQuestBoardAnsBodyTv.text = adminReportComment.admin_report_comment_body
            binding.itemAdminQuestBoardAnsDateTv.text = adminReportComment.admin_report_comment_time
        }
    }
}
