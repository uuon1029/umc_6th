package com.example.umc_6th.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.RecentSearchRVAdapter
import com.example.umc_6th.databinding.ItemSearchBinding

class AdminQuestRecentSearchRVAdapter(
    private var recentsearchList: ArrayList<String>,
    private val onItemRemove: (Int) -> Unit,
    private val onItemClick : (String) -> Unit
): RecyclerView.Adapter<AdminQuestRecentSearchRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AdminQuestRecentSearchRVAdapter.ViewHolder {
        val  binding : ItemSearchBinding = ItemSearchBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdminQuestRecentSearchRVAdapter.ViewHolder, position: Int) {
        holder.bind(recentsearchList[position])
    }

    override fun getItemCount(): Int = recentsearchList.size

    inner class ViewHolder(val binding: ItemSearchBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(recentSearch: String){
            binding.itemSearchTextTv.text = recentSearch
            binding.root.setOnClickListener {
                onItemClick(recentSearch)
            }
            binding.itemSearchDeleteIv.setOnClickListener {
                onItemRemove(adapterPosition)
            }
        }
    }
}