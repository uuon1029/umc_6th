package com.example.umc_6th.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Data.MajorID
import com.example.umc_6th.R

class MajorAdapter(private val majors: List<MajorID>) : RecyclerView.Adapter<MajorAdapter.MajorViewHolder>() {

    inner class MajorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val majorName: TextView = itemView.findViewById(R.id.majorName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MajorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.major_item, parent, false)
        return MajorViewHolder(view)
    }

    override fun onBindViewHolder(holder: MajorViewHolder, position: Int) {
        holder.majorName.text = majors[position].toString()
    }

    override fun getItemCount(): Int {
        return majors.size
    }
}
