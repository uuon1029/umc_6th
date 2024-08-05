package com.example.umc_6th.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Data.AdminQuest
import com.example.umc_6th.databinding.ItemAdminQuestBoardBinding
import com.example.umc_6th.databinding.ItemConfigInquireQnaBinding

class ConfigInquireQnaRVAdapter(var inquireqnalist: ArrayList<AdminQuest>) : RecyclerView.Adapter<ConfigInquireQnaRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConfigInquireQnaRVAdapter.ViewHolder {
        val binding = ItemConfigInquireQnaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = inquireqnalist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(inquireqnalist[position])
    }

    inner class ViewHolder(private val binding: ItemConfigInquireQnaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(adminQuest: AdminQuest) {
            binding.itemInquireQnaFilterTv.text = adminQuest.adminquest_filter
            binding.itemInquireQnaTitleTv.text = adminQuest.adminquest_title
            binding.itemInquireQnaDateTv.text = adminQuest.adminquest_date
        }
    }
}