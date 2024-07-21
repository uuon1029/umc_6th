package com.example.umc_6th

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.databinding.ItemMoreBinding

class MoreTotalBoardRVAdapter() : RecyclerView.Adapter<MoreTotalBoardRVAdapter.ViewHolder>() {
    var moreTotalBoardlist = ArrayList<More>()

    inner class ViewHolder(private val binding: ItemMoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(moreMajor: More) {
            binding.itemMoreTitleTv.text = moreMajor.more_title
            binding.itemMoreBodyTv.text = moreMajor.more_body
            binding.itemMoreTimeTv.text = moreMajor.more_time.toString()
            binding.itemMoreChatnumTv.text = moreMajor.more_chatnum.toString()
            binding.itemMoreLikenumTv.text = moreMajor.more_likenum.toString()
            binding.itemMoreImgIv.setImageResource(moreMajor.more_coverImg!!)
            binding.itemMoreNameTv.text = moreMajor.more_name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMoreBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }
    override fun getItemCount(): Int  = moreTotalBoardlist.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(moreTotalBoardlist[position])
    }

}