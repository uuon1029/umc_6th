package com.example.umc_6th

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.databinding.ItemQuestSubAnswerBinding

class SubAnswerRVAdapter(val itemList : ArrayList<SubAnswer>, private val itemClickListener: MainAnswerRVAdapter.OnItemClickListener): RecyclerView.Adapter<SubAnswerRVAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemQuestSubAnswerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
        holder.binding.itemQuestSubAnwserLikeIv.setOnClickListener {
            holder.binding.itemQuestSubAnwserLikeIv.visibility = View.GONE
            holder.binding.itemQuestSubAnwserUnlikeIv.visibility = View.VISIBLE
        }
        holder.binding.itemQuestSubAnwserUnlikeIv.setOnClickListener {
            holder.binding.itemQuestSubAnwserLikeIv.visibility = View.VISIBLE
            holder.binding.itemQuestSubAnwserUnlikeIv.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = itemList.size

    inner class Holder(val binding: ItemQuestSubAnswerBinding, private val itemClickListener: MainAnswerRVAdapter.OnItemClickListener) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: SubAnswer){
            binding.itemQuestSubAnwserNameTv.text = item.sub_answer_name
            binding.itemQuestSubAnwserTimeTv.text = item.var_answer_date.toString()
            binding.itemQuestSubAnwserBodyTv.text = item.var_answer_body

            binding.itemQuestSubAnswerProfileIv.setOnClickListener {
                itemClickListener.onSubProfileImageClick(adapterPosition)
            }
        }
    }

}