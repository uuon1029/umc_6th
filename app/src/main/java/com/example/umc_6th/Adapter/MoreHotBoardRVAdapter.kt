package com.example.umc_6th

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Retrofit.DataClass.Board
import com.example.umc_6th.databinding.ItemMoreBinding

class MoreHotBoardRVAdapter() : RecyclerView.Adapter<MoreHotBoardRVAdapter.ViewHolder>() {
    var morehotBoardlist = ArrayList<Board>()

    fun interface MyItemClickListener{
        fun onItemClick(more: Board)

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
        holder.bind(morehotBoardlist[position])
        holder.itemView.setOnClickListener {
            myItemClickListener.onItemClick(morehotBoardlist[position])
        }
    }
    override fun getItemCount(): Int  = morehotBoardlist.size

    inner class ViewHolder(private val binding: ItemMoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(board: Board) {
            binding.itemMoreTitleTv.text = board.title
            binding.itemMoreBodyTv.text = board.content
            binding.itemMoreTimeTv.text = board.boardDate
            binding.itemMoreChatnumTv.text = board.pinCount.toString()
            binding.itemMoreLikenumTv.text = board.likeCount.toString()
            if (board.picPreview != null) {
                binding.itemMoreImgIv.setImageResource(board.picPreview.toInt())
            }
            binding.itemMoreNameTv.text = board.userNickName
        }
    }

}