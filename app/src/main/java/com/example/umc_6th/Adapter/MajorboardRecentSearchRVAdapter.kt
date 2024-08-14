package com.example.umc_6th.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.databinding.ItemSearchBinding

class MajorboardRecentSearchRVAdapter(
    private var majorrecentsearchList: ArrayList<String>,
    private val onItemRemove: (Int) -> Unit,
    private val onItemClick : (String) -> Unit
): RecyclerView.Adapter<MajorboardRecentSearchRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MajorboardRecentSearchRVAdapter.ViewHolder {
        val  binding : ItemSearchBinding = ItemSearchBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MajorboardRecentSearchRVAdapter.ViewHolder, position: Int) {
        holder.bind(majorrecentsearchList[position])
    }

    override fun getItemCount(): Int = majorrecentsearchList.size

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