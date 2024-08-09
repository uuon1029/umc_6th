package com.example.umc_6th

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Data.ProfileBoard
import com.example.umc_6th.databinding.ItemProfileBoardBinding

class AdminProfileBoardRVAdapter(var adminprofileboardlist: ArrayList<ProfileBoard>) : RecyclerView.Adapter<AdminProfileBoardRVAdapter.ViewHolder>(){

    fun interface MyItemClickListener{
        fun onItemClick(profileBoard: ProfileBoard)

    }
    private lateinit var myItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        myItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProfileBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    override fun getItemCount(): Int = adminprofileboardlist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(adminprofileboardlist[position])
        holder.itemView.setOnClickListener {
            myItemClickListener.onItemClick(adminprofileboardlist[position])
        }
    }

    inner class ViewHolder(private val binding: ItemProfileBoardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(profileBoard: ProfileBoard) {
            binding.itemProfileBoardTitleTv.text = profileBoard.admin_profile_board_title
            binding.itemProfileBoardBodyTv.text = profileBoard.admin_profile_board_body
            binding.itemProfileBoardDateTv.text = profileBoard.admin_profile_board_date
        }
    }
}