package com.example.umc_6th

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.databinding.ItemMoreMajorBinding

class MoreMajorRVAdapter() : RecyclerView.Adapter<MoreMajorRVAdapter.ViewHolder>() {
    var moreMajorlist = ArrayList<MoreMajor>()

    inner class ViewHolder(private val binding: ItemMoreMajorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(moreMajor: MoreMajor) {
            binding.itemMoreMajorTitleTv.text = moreMajor.moremajor_title
            binding.itemMoreMajorBodyTv.text = moreMajor.moremajor_body
            binding.itemMoreMajorChatIv.setImageResource(moreMajor.moremajor_coverImg!!)
            binding.itemMoreMajorTimeTv.text = moreMajor.moremajor_time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMoreMajorBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }
    override fun getItemCount(): Int  = moreMajorlist.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(moreMajorlist[position])
    }

}