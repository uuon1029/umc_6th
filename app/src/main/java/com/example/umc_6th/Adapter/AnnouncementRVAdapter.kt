package com.example.umc_6th

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Retrofit.DataClass.Announcement
import com.example.umc_6th.Retrofit.DataClass.Board
import com.example.umc_6th.databinding.ItemConfigAnnouncementBinding

class AnnouncementRVAdapter() : RecyclerView.Adapter<AnnouncementRVAdapter.ViewHolder>() {
    var announcementlist = ArrayList<Announcement>()

    fun interface MyItemClickListener{
        fun onItemClick(announcement: Announcement)

    }
    private lateinit var myItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        myItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemConfigAnnouncementBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }
    override fun getItemCount(): Int  = announcementlist.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(announcementlist[announcementlist.size-position-1])
        holder.itemView.setOnClickListener{
            myItemClickListener.onItemClick(announcementlist[position])
        }
    }
    inner class ViewHolder(private val binding: ItemConfigAnnouncementBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(announcement: Announcement) {
            binding.itemAnnouncementNumTv.text = announcement.id.toString()
            binding.itemAnnouncementBodyTv.text = announcement.title

            val date = announcement.updatedAt
            binding.itemAnnouncementDateTv.text = (date.substring(5,7)
                    + "." + date.substring(8,10))

        }
    }

}