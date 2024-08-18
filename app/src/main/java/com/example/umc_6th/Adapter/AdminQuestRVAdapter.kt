package com.example.umc_6th.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Activity.AdminQuestActivity
import com.example.umc_6th.Retrofit.DataClass.Faq
import com.example.umc_6th.databinding.ItemAdminQuestBoardBinding

class AdminQuestRVAdapter(
    private val context: AdminQuestActivity,
    var adminquestlist: ArrayList<Faq>
) : RecyclerView.Adapter<AdminQuestRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onDeleteClick(item: Faq)
        fun onModifyClick(item: Faq) //수정 이벤트 처리

    }

    private lateinit var myItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        myItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdminQuestRVAdapter.ViewHolder {
        val binding = ItemAdminQuestBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = adminquestlist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = adminquestlist[position]
        holder.bind(item)

        holder.binding.itemAdminQuestMainCl.setOnClickListener {
            holder.binding.itemAdminQuestDeleteCl.visibility = if (holder.binding.itemAdminQuestDeleteCl.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        holder.binding.itemAdminQuestDeleteRemoveTv.setOnClickListener {
            myItemClickListener.onDeleteClick(item)
        }

        holder.binding.itemAdminQuestDeleteModifyTv.setOnClickListener {
            myItemClickListener.onModifyClick(item)  // 수정 버튼 클릭 이벤트 처리
        }
    }

    inner class ViewHolder(val binding: ItemAdminQuestBoardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(faq: Faq) {
            binding.itemAdminQuestFilterTv.text = faq.category
            binding.itemAdminQuestTitleTv.text = faq.title
            binding.itemAdminQuestDateTv.text = faq.updateAt
        }
    }
    fun removeItem(faq: Faq) {
        val position = adminquestlist.indexOf(faq)
        if (position != -1) {
            adminquestlist.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}