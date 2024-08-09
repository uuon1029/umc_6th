package com.example.umc_6th

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_6th.Retrofit.DataClass.Board
import com.example.umc_6th.databinding.ItemMoreBinding

class MoreTotalBoardRVAdapter() : RecyclerView.Adapter<MoreTotalBoardRVAdapter.ViewHolder>() {
    var moreTotalBoardlist = ArrayList<Board>()

    fun interface MyItemClickListener{
        fun onItemClick(board: Board)

    }
    private lateinit var myItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        myItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMoreBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(moreTotalBoardlist[position])
        holder.itemView.setOnClickListener {
            myItemClickListener.onItemClick(moreTotalBoardlist[position])
        }
    }
    override fun getItemCount(): Int  = moreTotalBoardlist.size
    inner class ViewHolder(private val binding: ItemMoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(board: Board) {
            binding.itemMoreTitleTv.text = board.title
            binding.itemMoreBodyTv.text = board.content
            binding.itemMoreTimeTv.text = board.boardDate
            binding.itemMoreChatnumTv.text = board.pinCount.toString()
            binding.itemMoreLikenumTv.text = board.likeCount.toString()
            binding.itemMoreNameTv.text = board.userNickName

            val picPreviewUrl = board.picPreview
            if (picPreviewUrl != null && picPreviewUrl.isNotEmpty()) {
                setImage(binding.itemMoreImgIv, picPreviewUrl)
            } else {
                binding.itemMoreImgIv.visibility = View.GONE
            }
        }
    }
    private fun setImage(view: ImageView, url: String) {
        Glide.with(view.context).load(url).into(view)
    }
}