package com.example.umc_6th

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.databinding.ItemConfigNoticeBinding
import com.example.umc_6th.databinding.ItemHomeBoardBinding

class ConfigNoticeRVAdapter() : RecyclerView.Adapter<ConfigNoticeRVAdapter.ViewHolder>() {
    var confignoticelist = ArrayList<ConfigNotice>()

    inner class ViewHolder(private val binding: ItemConfigNoticeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(configNotice: ConfigNotice) {
            binding.itemConfigNoticeNumTv.text = configNotice.confignotice_no.toString()
            binding.itemConfigNoticeBodyTv.text = configNotice.confignotice_body
            binding.itemConfigNoticeDateTv.text = configNotice.confignotice_date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemConfigNoticeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }
    override fun getItemCount(): Int  = confignoticelist.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(confignoticelist[position])
    }

}