package com.example.umc_6th.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Data.AdminQuest
import com.example.umc_6th.databinding.ItemAdminQuestBoardBinding

class AdminQuestRVAdapter(var adminquestlist: ArrayList<AdminQuest>) : RecyclerView.Adapter<AdminQuestRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdminQuestRVAdapter.ViewHolder {
        val binding = ItemAdminQuestBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = adminquestlist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(adminquestlist[position])
    }

    inner class ViewHolder(private val binding: ItemAdminQuestBoardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(adminQuest: AdminQuest) {
            binding.itemAdminQuestFilterTv.text = adminQuest.adminquest_filter
            binding.itemAdminQuestTitleTv.text = adminQuest.adminquest_title
            binding.itemAdminQuestDateTv.text = adminQuest.adminquest_date
        }
    }
}