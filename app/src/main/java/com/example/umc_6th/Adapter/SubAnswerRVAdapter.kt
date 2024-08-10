package com.example.umc_6th

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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

        private fun setImage(view: ImageView, url:String) {
            Glide.with(QuestActivity()).load(url).into(view)
        }
        fun bind(item: PinComment){
            binding.itemQuestSubAnwserNameTv.text = item.userNickname
            binding.itemQuestSubAnwserTimeTv.text = item.pinCommentDate
            binding.itemQuestSubAnwserBodyTv.text = item.comment


            val imgList = item.pinPicList
            val size: Int = imgList.size
            when (size) {
                1 -> {
                    setImage(binding.itemQuestMainAnwserImg1Iv, imgList[0])
                }
                2 -> {
                    setImage(binding.itemQuestMainAnwserImg1Iv, imgList[0])
                    setImage(binding.itemQuestMainAnwserImg2Iv, imgList[1])
                }
                3 -> {
                    setImage(binding.itemQuestMainAnwserImg1Iv, imgList[0])
                    setImage(binding.itemQuestMainAnwserImg2Iv, imgList[1])
                    setImage(binding.itemQuestMainAnwserImg3Iv, imgList[2])
                }
            }

            binding.itemQuestMainAnwserImg1Iv.visibility = if (size > 0) View.VISIBLE else View.GONE
            binding.itemQuestMainAnwserImg2Iv.visibility = if (size > 1) View.VISIBLE else View.GONE
            binding.itemQuestMainAnwserImg3Iv.visibility = if (size > 2) View.VISIBLE else View.GONE


            binding.itemQuestSubAnswerProfileIv.setOnClickListener {
                itemClickListener.onSubProfileImageClick(adapterPosition)
            }
        }
    }

}