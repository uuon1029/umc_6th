package com.example.umc_6th.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.MainActivity
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.DataClass.Faq
import com.example.umc_6th.Retrofit.Response.RootFAQDeleteResponse
import com.example.umc_6th.databinding.ItemConfigInquireQnaBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfigInquireQnaRVAdapter(var inquireqnalist: ArrayList<Faq>) : RecyclerView.Adapter<ConfigInquireQnaRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(faq: Faq)

    }

    private lateinit var myItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        myItemClickListener = itemClickListener
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConfigInquireQnaRVAdapter.ViewHolder {
        val binding = ItemConfigInquireQnaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = inquireqnalist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = inquireqnalist[position]
        holder.bind(item)

        holder.bind(inquireqnalist[position])

        holder.binding.itemInquireQnaMainCl.setOnClickListener {
            holder.binding.itemInquireQnaAnswerCl.visibility = if (holder.binding.itemInquireQnaAnswerCl.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

    }

    inner class ViewHolder(val binding: ItemConfigInquireQnaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(faqlist: Faq) {
            binding.itemInquireQnaFilterTv.text = faqlist.category
            binding.itemInquireQnaTitleTv.text = faqlist.title
            binding.itemInquireQnaDateTv.text = faqlist.content
        }
    }
}