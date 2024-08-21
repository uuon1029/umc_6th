package com.example.umc_6th.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Retrofit.DataClass.Faq
import com.example.umc_6th.databinding.ItemConfigInquireQnaBinding

class ConfigInquireQnaRVAdapter(
    private var inquireqnalist: ArrayList<Faq>,
    private val selectedCategory: String
) : RecyclerView.Adapter<ConfigInquireQnaRVAdapter.ViewHolder>() {

    private var selectedPosition: Int = -1

    interface MyItemClickListener {
        fun initRv()
    }

    private lateinit var myItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        myItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemConfigInquireQnaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(inquireqnalist[position])

        holder.itemView.setOnClickListener {
            holder.binding.itemInquireQnaAnswerCl.visibility =
                if (holder.binding.itemInquireQnaAnswerCl.visibility == View.VISIBLE){
                    holder.binding.itemInquireQnaFilterTv.isSelected = false
                    View.GONE
                }
                else{
                    holder.binding.itemInquireQnaFilterTv.isSelected = true
                    View.VISIBLE
                }
        }
    }

    override fun getItemCount(): Int = inquireqnalist.size


    inner class ViewHolder(val binding: ItemConfigInquireQnaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(faqlist: Faq) {
            binding.itemInquireQnaFilterTv.text = faqlist.category
            binding.itemInquireQnaTitleTv.text = faqlist.title
            binding.itemInquireQnaDateTv.text = faqlist.updateAt
        }
    }
}
