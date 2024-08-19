package com.example.umc_6th.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Config1to1CheckList
import com.example.umc_6th.Retrofit.DataClass.Qna
import com.example.umc_6th.databinding.ItemConfig1to1CheckBinding
import com.example.umc_6th.databinding.ItemConfig1to1CheckListBinding


class Config1to1CheckListRVAdapter() : RecyclerView.Adapter<Config1to1CheckListRVAdapter.ViewHolder>() {
    var config1to1checklist = ArrayList<Qna>()

    fun interface MyItemClickListener{
            fun onItemClick(qna: Qna)

    }
    private lateinit var myItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        myItemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemConfig1to1CheckBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }
    override fun getItemCount(): Int  = config1to1checklist.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(config1to1checklist[position])
        holder.itemView.setOnClickListener{
            myItemClickListener.onItemClick(config1to1checklist[position])
        }
    }


    inner class ViewHolder(private val binding: ItemConfig1to1CheckBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(qna: Qna) {
            binding.item1to1CheckListCheckTv.text = qna.status
            binding.item1to1CheckListTitleTv.text = qna.title
            binding.item1to1CheckListDateTv.text = qna.createdAt
        }

    }
}