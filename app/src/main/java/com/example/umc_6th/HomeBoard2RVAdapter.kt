package com.example.umc_6th

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.databinding.ItemHomeBoardBinding

class HomeBoard2RVAdapter() : RecyclerView.Adapter<HomeBoard2RVAdapter.ViewHolder>() {
    var homeboardlist = ArrayList<HomeBoard>()

    inner class ViewHolder(private val binding: ItemHomeBoardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(homeBoard: HomeBoard) {
            binding.itemHomeBoardBodyTv.text = homeBoard.homeboard_body
            binding.itemHomeBoardHeartIv.setImageResource(homeBoard.homeboard_heart!!)
            binding.itemHomeBoardHeartnumTv.text = homeBoard.homeboard_heartnum
            binding.itemHomeBoardChatIv.setImageResource(homeBoard.homeboard_chat!!)
            binding.itemHomeBoardChatnumTv.text = homeBoard.homeboard_chatnum
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