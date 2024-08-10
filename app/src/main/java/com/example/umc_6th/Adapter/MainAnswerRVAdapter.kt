package com.example.umc_6th

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_6th.Retrofit.DataClass.Pin
import com.example.umc_6th.Retrofit.DataClass.PinComment
import com.example.umc_6th.databinding.ItemQuestMainAnswerBinding

class MainAnswerRVAdapter(private val context: Context, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<MainAnswerRVAdapter.Holder>() {
    var itemList = ArrayList<Pin>()

    interface OnItemClickListener {
        fun onProfileImageClick(position: Int)
        fun onSubProfileImageClick(position: Int)
        fun onCommentDeleteClick(pinId: Int, userId: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemQuestMainAnswerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, context, itemClickListener )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
        holder.binding.itemQuestMainAnwserLikeIv.setOnClickListener {
            holder.binding.itemQuestMainAnwserLikeIv.visibility = View.GONE
            holder.binding.itemQuestMainAnwserUnlikeIv.visibility = View.VISIBLE
        }
        holder.binding.itemQuestMainAnwserUnlikeIv.setOnClickListener {
            holder.binding.itemQuestMainAnwserLikeIv.visibility = View.VISIBLE
            holder.binding.itemQuestMainAnwserUnlikeIv.visibility = View.GONE
        }
        holder.binding.itemQuestMainAnswerDeleteCl.setOnClickListener {
            val pinId = item.id
            val userId = item.userId
            itemClickListener.onCommentDeleteClick(pinId, userId)
        }
    }

    override fun getItemCount(): Int = itemList.size

    class Holder(val binding: ItemQuestMainAnswerBinding, private val context: Context, private val itemClickListener: OnItemClickListener) : RecyclerView.ViewHolder(binding.root) {

        private fun setImage(view: ImageView,url:String) {
            Glide.with(QuestActivity()).load(url).into(view)
        }
        fun bind(item: Pin) {
            binding.itemQuestMainAnwserNameTv.text = item.userNickname
            binding.itemQuestMainAnwserBodyTv.text = item.comment
            binding.itemQuestMainAnwserTimeTv.text = item.pinDate

            val imgList = item.pinPictureList
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

            val subAnswerAdapter = SubAnswerRVAdapter(item.pinCommentList?: ArrayList(), itemClickListener)
            binding.itemQuestMainAnwserSubRv.adapter = subAnswerAdapter
            binding.itemQuestMainAnwserSubRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            binding.itemQuestMainAnwserProfileIv.setOnClickListener {
                itemClickListener.onProfileImageClick(adapterPosition)
            }
        }
    }
}
