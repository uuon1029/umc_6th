package com.example.umc_6th

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.databinding.ItemConfigNoticeBinding

class AnnouncementRVAdapter() : RecyclerView.Adapter<AnnouncementRVAdapter.ViewHolder>() {
    var announcementlist = ArrayList<Announcement>()

    inner class ViewHolder(private val binding: ItemConfigNoticeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(announcement: Announcement) {
            binding.itemAnnouncementNumTv.text = announcement.announcement_no.toString()
            binding.itemAnnouncementBodyTv.text = announcement.announcement_body
            binding.itemAnnouncementDateTv.text = announcement.announcement_date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemConfigNoticeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }
    override fun getItemCount(): Int  = announcementlist.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(announcementlist[position])
    }

}