package com.example.umc_6th.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Data.AdminQuest
import com.example.umc_6th.MainActivity
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.DataClass.Board
import com.example.umc_6th.Retrofit.DataClass.Faq
import com.example.umc_6th.Retrofit.DataClass.Qna
import com.example.umc_6th.Retrofit.Response.RootFAQDeleteResponse
import com.example.umc_6th.databinding.ItemAdminQuestBoardBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminQuestRVAdapter(var adminquestlist: ArrayList<Faq>) : RecyclerView.Adapter<AdminQuestRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(faq: Faq)
        fun onDeleteClick(position: Int)

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
            CookieClient.service.deleteFAQ(MainActivity.accessToken, item.faqid).enqueue(object : Callback<RootFAQDeleteResponse>{
                override fun onResponse(call: Call<RootFAQDeleteResponse>, response: Response<RootFAQDeleteResponse>) {
                    if (response.isSuccessful && response.body()?.isSuccess == true) {
                        myItemClickListener.onDeleteClick(item.faqid)
                        adminquestlist.removeAt(holder.bindingAdapterPosition)
                        notifyItemRemoved(holder.bindingAdapterPosition)
                        Log.d("adminquestdelete", response.toString())
                    } else {
                        Log.e("AdminQuestRVAdapter", "FAQ 삭제 실패: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<RootFAQDeleteResponse>, t: Throwable) {
                    Log.e("DeleteError", "Failed to delete comment", t)
                }
            })
        }
    }

    inner class ViewHolder(val binding: ItemAdminQuestBoardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(faq: Faq) {
            binding.itemAdminQuestFilterTv.text = faq.category
            binding.itemAdminQuestTitleTv.text = faq.title
            binding.itemAdminQuestDateTv.text = faq.updateAt


        }
    }
}