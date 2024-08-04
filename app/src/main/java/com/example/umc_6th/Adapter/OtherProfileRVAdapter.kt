package com.example.umc_6th

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Data.ProfileBoard
import com.example.umc_6th.databinding.ActivityOtherProfileBinding
import com.example.umc_6th.databinding.ItemHomeBoardBinding

class OtherProfileRVAdapter(var otherprofilelist: ArrayList<OtherProfile>) : RecyclerView.Adapter<OtherProfileRVAdapter.ViewHolder>(){

    fun interface MyItemClickListener{
        fun onItemClick(otherProfile: OtherProfile)

    }
    private lateinit var myItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        myItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomeBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    override fun getItemCount(): Int = otherprofilelist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(otherprofilelist[position])
    }

    inner class ViewHolder(private val binding: ItemHomeBoardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(otherProfile: OtherProfile) {
            binding.itemHomeBoardBodyTv.text = otherProfile.otherprofile_body
            binding.itemHomeBoardChatnumTv.text = otherProfile.otherprofile_chat.toString()
            binding.itemHomeBoardHeartnumTv.text = otherProfile.otherprofile_like.toString()
        }
    }
}