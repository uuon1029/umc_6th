package com.example.umc_6th

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Retrofit.DataClass.ProfileBoard
import com.example.umc_6th.databinding.ActivityOtherProfileBinding
import com.example.umc_6th.databinding.ItemHomeBoardBinding

class OtherProfileRVAdapter(var boardList: ArrayList<ProfileBoard>) : RecyclerView.Adapter<OtherProfileRVAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomeBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    override fun getItemCount(): Int = boardList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(boardList[position])
    }

    inner class ViewHolder(private val binding: ItemHomeBoardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(board: ProfileBoard) {
            binding.itemHomeBoardBodyTv.text = board.title
            binding.itemHomeBoardChatnumTv.text = board.pinCount.toString()
            binding.itemHomeBoardHeartnumTv.text = board.likeCount.toString()
        }
    }
}