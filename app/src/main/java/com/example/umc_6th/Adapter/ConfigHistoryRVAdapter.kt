package com.example.umc_6th.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Config
import com.example.umc_6th.Retrofit.DataClass.History
import com.example.umc_6th.databinding.ItemHistoryBinding

class ConfigHistoryRVAdapter(private var configList:ArrayList<History>): RecyclerView.Adapter<ConfigHistoryRVAdapter.ViewHolder>() {

    fun interface MyOnClickeListener {
        fun itemClick(boardId : Int)
    }

    private lateinit var myOnClickeListener: MyOnClickeListener

    fun setClickListener(onClickeListener: MyOnClickeListener){
        myOnClickeListener = onClickeListener
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ConfigHistoryRVAdapter.ViewHolder {
        val binding : ItemHistoryBinding = ItemHistoryBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConfigHistoryRVAdapter.ViewHolder, position: Int) {
        holder.bind(configList[position])

        holder.itemView.setOnClickListener {
            myOnClickeListener.itemClick(configList[position].boardId)
        }
    }

    override fun getItemCount(): Int = configList.size

    inner class ViewHolder(val binding: ItemHistoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(history: History){
            binding.itemConfigTypeTv.text = history.header
            binding.itemConfigContentTitleTv.text = history.title
            binding.itemConfigContentTv.text = history.content
            binding.itemConfigDateTv.text = history.createdAt
//            binding.itemConfigDateTv.text = String.format("%d",config.date)
        }
    }
}