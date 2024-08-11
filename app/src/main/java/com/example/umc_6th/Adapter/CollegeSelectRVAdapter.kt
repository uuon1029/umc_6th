package com.example.umc_6th.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Data.CollegeID
import com.example.umc_6th.Data.MajorID
import com.example.umc_6th.databinding.ItemMajorBinding

class CollegeSelectRVAdapter(var collegeList:List<CollegeID>) : RecyclerView.Adapter<CollegeSelectRVAdapter.ViewHolder>() {

    fun interface MyOnClickeListener {
        fun itemClick(college: CollegeID)
    }

    private lateinit var myOnClickeListener: MyOnClickeListener

    fun setClickListener(onClickeListener: MyOnClickeListener){
        myOnClickeListener = onClickeListener
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemMajorBinding = ItemMajorBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(collegeList[position],position)

        holder.itemView.setOnClickListener {
            myOnClickeListener.itemClick(collegeList[position])
        }
    }

    override fun getItemCount(): Int = collegeList.size

    inner class ViewHolder(val binding: ItemMajorBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(college : CollegeID,position: Int){
            binding.itemMajorTv.text = college.name
            if (collegeList.size -1 == position) {
                binding.itemMajorLine.visibility = View.GONE
            }
        }
    }
}