package com.example.umc_6th.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Retrofit.DataClass.Announcement
import com.example.umc_6th.Retrofit.DataClass.Faq
import com.example.umc_6th.databinding.ItemConfigAnnouncementBinding

class AdminAnnouncementRVAdapter(var adminAnnouncementlist: ArrayList<Announcement>) : RecyclerView.Adapter<AdminAnnouncementRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(announcement: Announcement)
        fun onDeleteClick(position: Int)

    }

    private lateinit var myItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        myItemClickListener = itemClickListener
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdminAnnouncementRVAdapter.ViewHolder {
        val binding = ItemConfigAnnouncementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = adminAnnouncementlist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(adminAnnouncementlist[position])
    }

    inner class ViewHolder(private val binding: ItemConfigAnnouncementBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(announcement: Announcement) {
            binding.itemAnnouncementNumTv.text = announcement.id.toString()
            binding.itemAnnouncementBodyTv.text = announcement.title
            binding.itemAnnouncementDateTv.text = announcement.createdAt
        }
    }
}