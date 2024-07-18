package com.example.umc_6th

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.databinding.ItemHomeBoardBinding

class OtherProfile1RVAdapter() : RecyclerView.Adapter<OtherProfile1RVAdapter.ViewHolder>() {
    var otherprofilelist = ArrayList<OtherProfile>()

    inner class ViewHolder(private val binding: ItemHomeBoardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(otherProfile: OtherProfile) {
            binding.itemHomeBoardBodyTv.text = otherProfile.otherprofile_body
            binding.itemHomeBoardChatnumTv.text = otherProfile.otherprofile_chat.toString()
            binding.itemHomeBoardHeartnumTv.text = otherProfile.otherprofile_like.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomeBoardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }
    override fun getItemCount(): Int  = otherprofilelist.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(otherprofilelist[position])
    }

}