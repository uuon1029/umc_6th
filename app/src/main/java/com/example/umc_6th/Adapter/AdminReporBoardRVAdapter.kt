package com.example.umc_6th.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Data.AdminReportBoard
import com.example.umc_6th.databinding.ItemAdminReportBoardAnsBinding

class AdminReportBoardRVAdapter(var adminreportboardlist: ArrayList<AdminReportBoard>) : RecyclerView.Adapter<AdminReportBoardRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdminReportBoardRVAdapter.ViewHolder {
        val binding = ItemAdminReportBoardAnsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = adminreportboardlist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(adminreportboardlist[position])
    }

    inner class ViewHolder(private val binding: ItemAdminReportBoardAnsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(adminReportBoard: AdminReportBoard) {
            binding.itemAdminQuestBoardAnsProfileIv.setImageResource(adminReportBoard.admin_report_board_img!!)
            binding.itemAdminQuestBoardAnsNameTv.text = adminReportBoard.admin_report_board_name
            binding.itemAdminQuestBoardAnsBodyTv.text = adminReportBoard.admin_report_board_body
            binding.itemAdminQuestBoardAnsDateTv.text = adminReportBoard.admin_report_board_date
        }
    }
}