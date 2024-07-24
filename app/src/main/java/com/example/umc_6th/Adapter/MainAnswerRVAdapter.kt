package com.example.umc_6th

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.databinding.ItemQuestMainAnswerBinding

class MainAnswerRVAdapter(private val context: Context) : RecyclerView.Adapter<MainAnswerRVAdapter.Holder>() {
    var itemList = ArrayList<MainAnswer>()

//    interface SetClick {
//
//    }
//    private lateinit var setClick : SetClick
//    fun initSetClick(itemSetClick : SetClick) {
//        setClick = itemSetClick
//    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemQuestMainAnswerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, context)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
        holder.binding.itemQuestMainAnwserLikeIv.setOnClickListener {
            holder.binding.itemQuestMainAnwserLikeIv.visibility = View.GONE
            holder.binding.itemQuestMainAnwserUnlikeIv.visibility = View.VISIBLE
        }
        holder.binding.itemQuestMainAnwserUnlikeIv.setOnClickListener {
            holder.binding.itemQuestMainAnwserLikeIv.visibility = View.VISIBLE
            holder.binding.itemQuestMainAnwserUnlikeIv.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = itemList.size

    class Holder(val binding: ItemQuestMainAnswerBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MainAnswer) {
            binding.itemQuestMainAnwserNameTv.text = item.main_answer_name
            binding.itemQuestMainAnwserBodyTv.text = item.main_answer_body
            binding.itemQuestMainAnwserTimeTv.text = item.main_answer_date.toString()

            val subAnswerAdapter = SubAnswerRVAdapter(item.main_answer_sublist)
            binding.itemQuestMainAnwserSubRv.adapter = subAnswerAdapter
            binding.itemQuestMainAnwserSubRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }
}
