package com.example.umc_6th.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Config
import com.example.umc_6th.databinding.ItemConfigBinding

class ConfigHistoryRVAdapter(private var configList:ArrayList<Config>): RecyclerView.Adapter<ConfigHistoryRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ConfigHistoryRVAdapter.ViewHolder {
        val binding : ItemConfigBinding = ItemConfigBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConfigHistoryRVAdapter.ViewHolder, position: Int) {
        holder.bind(configList[position])
    }

    override fun getItemCount(): Int = configList.size

    inner class ViewHolder(val binding: ItemConfigBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(config: Config){
            binding.itemConfigTypeTv.text = config.type
            binding.itemConfigContentTitleTv.text = config.title
            binding.itemConfigContentTv.text = config.content
//            binding.itemConfigDateTv.text = String.format("%d",config.date)
        }
    }
}