package com.example.umc_6th.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
            binding.itemAdminReportFilterTv.text = adminReport.adminreport_filter
            binding.itemAdminReportCheckTv.text = adminReport.adminreport_uncheck
            binding.itemAdminReportTitleTv.text = adminReport.adminreport_title
            binding.itemAdminReportDateTv.text = adminReport.adminreport_date

            binding.root.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, AdminReportBoardActivity::class.java)
                context.startActivity(intent)
            }
        }
    }
}