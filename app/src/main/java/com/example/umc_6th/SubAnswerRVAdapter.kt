package com.example.umc_6th

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.databinding.ItemQuestSubAnswerBinding

class SubAnswerRVAdapter(val itemList : ArrayList<SubAnswer>): RecyclerView.Adapter<SubAnswerRVAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemQuestSubAnswerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = itemList.size

    inner class Holder(var binding: ItemQuestSubAnswerBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: SubAnswer){
            binding.itemQuestSubAnwserNameTv.text = item.sub_answer_name
            binding.itemQuestSubAnwserTimeTv.text = item.var_answer_date.toString()
            binding.itemQuestSubAnwserBodyTv.text = item.var_answer_body
        }
    }

}