package com.example.umc_6th.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Config
import com.example.umc_6th.Retrofit.DataClass.History
import com.example.umc_6th.Retrofit.DataClass.RootQnA
import com.example.umc_6th.databinding.ItemConfig1to1CheckListBinding
import com.example.umc_6th.databinding.ItemHistoryBinding

class Admin1to1RVAdapter(private var RootQnAList:ArrayList<RootQnA>): RecyclerView.Adapter<Admin1to1RVAdapter.ViewHolder>() {

    fun interface MyOnClickeListener {
        fun itemClick(item:RootQnA)
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
        holder.bind(RootQnAList[position])

        holder.itemView.setOnClickListener {
            myOnClickeListener.itemClick(RootQnAList[position])
        }
    }

    override fun getItemCount(): Int = RootQnAList.size

    inner class ViewHolder(val binding: ItemConfig1to1CheckListBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(RootQnA: RootQnA){
            binding.item1to1CheckListTitleTv.text = RootQnA.title
            binding.item1to1CheckListBodyTv.text = RootQnA.content
            binding.item1to1CheckListDateTv.text = RootQnA.createdAt
            binding.item1to1CheckListCheckTv.text = RootQnA.status
//            binding.itemConfigDateTv.text = String.format("%d",config.date)
        }
    }
}