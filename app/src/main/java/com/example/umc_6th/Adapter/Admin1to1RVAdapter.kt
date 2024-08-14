package com.example.umc_6th.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Config
import com.example.umc_6th.Retrofit.DataClass.History
import com.example.umc_6th.Retrofit.DataClass.RootQNA
import com.example.umc_6th.databinding.ItemConfig1to1CheckListBinding
import com.example.umc_6th.databinding.ItemHistoryBinding

class Admin1to1RVAdapter(private var rootqnaList:ArrayList<RootQNA>): RecyclerView.Adapter<Admin1to1RVAdapter.ViewHolder>() {

    fun interface MyOnClickeListener {
        fun itemClick(qnaId : Int)
    }

    private lateinit var myOnClickeListener: MyOnClickeListener

    fun setClickListener(onClickeListener: MyOnClickeListener){
        myOnClickeListener = onClickeListener
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Admin1to1RVAdapter.ViewHolder {
        val binding : ItemConfig1to1CheckListBinding = ItemConfig1to1CheckListBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: Admin1to1RVAdapter.ViewHolder, position: Int) {
        holder.bind(rootqnaList[position])

        holder.itemView.setOnClickListener {
            myOnClickeListener.itemClick(rootqnaList[position].id)
        }
    }

    override fun getItemCount(): Int = rootqnaList.size

    inner class ViewHolder(val binding: ItemConfig1to1CheckListBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(rootqna: RootQNA){
            binding.item1to1CheckListTitleTv.text = rootqna.title
            binding.item1to1CheckListBodyTv.text = rootqna.content
            binding.item1to1CheckListDateTv.text = rootqna.createdAt
            binding.item1to1CheckListCheckTv.text = rootqna.status
//            binding.itemConfigDateTv.text = String.format("%d",config.date)
        }
    }
}