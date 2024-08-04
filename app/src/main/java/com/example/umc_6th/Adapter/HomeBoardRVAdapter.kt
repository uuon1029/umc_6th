package com.example.umc_6th

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Retrofit.Data.MainBoard
import com.example.umc_6th.databinding.ItemHomeBoardBinding

class HomeBoardRVAdapter() : RecyclerView.Adapter<HomeBoardRVAdapter.ViewHolder>() {
    var homeboardlist = ArrayList<MainBoard>()

    inner class ViewHolder(private val binding: ItemHomeBoardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(board: MainBoard) {
            binding.itemHomeBoardBodyTv.text = board.title
            binding.itemHomeBoardChatnumTv.text = board.pinCount.toString()
            binding.itemHomeBoardHeartnumTv.text = board.likeCount.toString()

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomeBoardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }
    override fun getItemCount(): Int  = homeboardlist.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(homeboardlist[position])
    }

}