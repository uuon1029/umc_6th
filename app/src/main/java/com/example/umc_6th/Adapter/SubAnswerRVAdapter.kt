package com.example.umc_6th

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Retrofit.DataClass.PinComment
import com.example.umc_6th.databinding.ItemQuestSubAnswerBinding

class SubAnswerRVAdapter(val itemList : ArrayList<PinComment>, private val itemClickListener: MainAnswerRVAdapter.OnItemClickListener): RecyclerView.Adapter<SubAnswerRVAdapter.Holder>() {

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
        holder.binding.itemQuestSubAnswerDeleteCl.setOnClickListener {
            val pinId = item.id
            val userId = item.userId
            itemClickListener.onCommentDeleteClick(pinId, userId)
        }
    }

    override fun getItemCount(): Int = itemList.size

    inner class Holder(val binding: ItemQuestSubAnswerBinding, private val itemClickListener: MainAnswerRVAdapter.OnItemClickListener) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: PinComment){
            binding.itemQuestSubAnwserNameTv.text = item.userNickname
            binding.itemQuestSubAnwserTimeTv.text = item.pinCommentDate
            binding.itemQuestSubAnwserBodyTv.text = item.comment

            binding.itemQuestSubAnswerProfileIv.setOnClickListener {
                itemClickListener.onSubProfileImageClick(adapterPosition)
            }
        }
    }

}