package com.example.umc_6th.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Activity.AdminReportBoardActivity
import com.example.umc_6th.Activity.AdminReportCommentActivity
import com.example.umc_6th.Data.AdminQuest
import com.example.umc_6th.Data.AdminReport
import com.example.umc_6th.databinding.ItemAdminQuestBoardBinding
import com.example.umc_6th.databinding.ItemAdminReportBoardBinding

class AdminReportRVAdapter(var adminreportlist: ArrayList<AdminReport>) : RecyclerView.Adapter<AdminReportRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdminReportRVAdapter.ViewHolder {
        val binding = ItemAdminReportBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = adminreportlist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(adminreportlist[position])
    }

    inner class ViewHolder(private val binding: ItemAdminReportBoardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(adminReport: AdminReport) {
            binding.itemAdminReportFilterTv.text = adminReport.type
            binding.itemAdminReportUncheckTv.text = getStatusText(adminReport.status)
            binding.itemAdminReportTitleTv.text = adminReport.title
            binding.itemAdminReportDateTv.text = adminReport.createdAt
            binding.itemAdminReportNumberTv.text = adminReport.report.toString()


            when (adminReport.type) {
                "게시글" -> {
                    binding.root.setOnClickListener {
                        val context = binding.root.context
                        val intent = Intent(context, AdminReportBoardActivity::class.java)
                        intent.putExtra("boardId",adminReport.id)
                        context.startActivity(intent)
                    }
                }
                "댓글" -> {
                    binding.root.setOnClickListener {
                        val context = binding.root.context
                        val intent = Intent(context, AdminReportCommentActivity::class.java)
                        intent.putExtra("boardId",adminReport.id)
                        context.startActivity(intent)
                    }
                }
            }

        }

        fun getStatusText(status: String): String {
            return when (status) {
                "WAITING" -> "확인 요망"
                else -> "처리 완료"
            }
        }
    }
}