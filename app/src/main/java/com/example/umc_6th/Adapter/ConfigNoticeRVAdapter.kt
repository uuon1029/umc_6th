package com.example.umc_6th.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Config
import com.example.umc_6th.databinding.ItemConfigBinding

class ConfigNoticeRVAdapter(private var noticeList:ArrayList<Config>): RecyclerView.Adapter<ConfigNoticeRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ConfigNoticeRVAdapter.ViewHolder {
        val binding : ItemConfigBinding = ItemConfigBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = noticeList.size


    override fun onBindViewHolder(holder: ConfigNoticeRVAdapter.ViewHolder, position: Int) {
        holder.bind(noticeList[position])
    }

    inner class ViewHolder(val binding: ItemConfigBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(config: Config){
            binding.itemConfigTypeTv.text = config.type
            binding.itemConfigContentTitleTv.text = config.title
            binding.itemConfigContentTv.text = config.content
//            binding.itemConfigDateTv.text = String.format("%d",config.date)
        }
    }
}