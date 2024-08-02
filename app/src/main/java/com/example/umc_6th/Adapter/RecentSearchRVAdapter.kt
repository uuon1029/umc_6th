package com.example.umc_6th

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.databinding.ItemSearchBinding

class RecentSearchRVAdapter(
    private var recentsearchList: ArrayList<String>,
    private val onItemRemove: (Int) -> Unit
):RecyclerView.Adapter<RecentSearchRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecentSearchRVAdapter.ViewHolder {
        val  binding : ItemSearchBinding = ItemSearchBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentSearchRVAdapter.ViewHolder, position: Int) {
        holder.bind(recentsearchList[position])
    }

    override fun getItemCount(): Int = recentsearchList.size

    inner class ViewHolder(val binding: ItemSearchBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(recentSearch: String){
            binding.itemSearchTextTv.text = recentSearch
            binding.itemSearchDeleteIv.setOnClickListener {
                onItemRemove(adapterPosition)
            }
        }
    }
}