package com.example.umc_6th.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Retrofit.DataClass.Faq
import com.example.umc_6th.databinding.ItemConfigInquireQnaBinding

class ConfigInquireQnaRVAdapter(var inquireqnalist: ArrayList<Faq>) : RecyclerView.Adapter<ConfigInquireQnaRVAdapter.ViewHolder>() {

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
        fun bind(faqlist: Faq) {
            binding.itemInquireQnaFilterTv.text = faqlist.category
            binding.itemInquireQnaTitleTv.text = faqlist.title
            binding.itemInquireQnaDateTv.text = faqlist.content
        }
    }
}