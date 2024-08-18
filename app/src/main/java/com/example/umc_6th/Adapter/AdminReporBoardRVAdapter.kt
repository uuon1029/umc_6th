package com.example.umc_6th.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_6th.Retrofit.DataClass.Complaint
import com.example.umc_6th.databinding.ItemAdminReportBoardAnsBinding

class AdminReportBoardRVAdapter(var adminreportboardlist: ArrayList<Complaint>) : RecyclerView.Adapter<AdminReportBoardRVAdapter.ViewHolder>() {

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
        fun bind(adminReportBoard: Complaint) {
            Glide.with(binding.itemAdminQuestBoardAnsProfileIv.context)
                .load(adminReportBoard.userPic)
                .into(binding.itemAdminQuestBoardAnsProfileIv)
            binding.itemAdminQuestBoardAnsNameTv.text = adminReportBoard.nickname
            binding.itemAdminQuestBoardAnsBodyTv.text = adminReportBoard.complaintContent
            binding.itemAdminQuestBoardAnsDateTv.text = adminReportBoard.createdAt
        }
    }
}