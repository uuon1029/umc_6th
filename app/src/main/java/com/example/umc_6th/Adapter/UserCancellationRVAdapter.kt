package com.example.umc_6th.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.databinding.ItemUserCancellationBinding

class UserCancellationRVAdapter : RecyclerView.Adapter<UserCancellationRVAdapter.ViewHolder>(){

    private val contentList
    = listOf(
        "쓰지 않는 앱이에요",
        "앱의 사용방법이 어려워요",
        "예제가 올바르지 않아요",
        "답안이 명확하지 않아요",
        "커뮤니티가 불편해요",
        "더이상 앱의 기능이 필요하지 않아요",
        "앱의 오류 때문에 불편해요",
        "기타"
    )

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemUserCancellationBinding = ItemUserCancellationBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = contentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(val binding:ItemUserCancellationBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int){
            binding.itemUserCancellationTv.text = contentList[position]
            if (position == contentList.size -1) {
                binding.itemUserCancellationLine.visibility = View.GONE
            }
        }

    }
}