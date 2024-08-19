package com.example.umc_6th.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_6th.Retrofit.DataClass.Faq
import com.example.umc_6th.databinding.ItemConfigInquireQnaBinding

class ConfigInquireQnaRVAdapter(
    private var inquireqnalist: ArrayList<Faq>,
    private val selectedCategory: String
) : RecyclerView.Adapter<ConfigInquireQnaRVAdapter.ViewHolder>() {

    private var selectedPosition: Int = -1

    interface MyItemClickListener {
        fun onItemClick(faq: Faq, position: Int)
    }

    private lateinit var myItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        myItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemConfigInquireQnaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = inquireqnalist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = inquireqnalist[position]
        holder.bind(item, position)

        holder.itemView.setOnClickListener {

            val oldPosition = selectedPosition
            selectedPosition = position

            notifyItemChanged(oldPosition)
            notifyItemChanged(selectedPosition)
        }
    }

    inner class ViewHolder(val binding: ItemConfigInquireQnaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(faqlist: Faq, position: Int) {
            binding.itemInquireQnaFilterTv.text = faqlist.category
            binding.itemInquireQnaTitleTv.text = faqlist.title
            binding.itemInquireQnaDateTv.text = faqlist.updateAt

            // 선택 상태 설정
            binding.itemInquireQnaFilterTv.isSelected = (position == selectedPosition)

            // 클릭 시 답변 표시
            binding.itemInquireQnaMainCl.setOnClickListener {
                binding.itemInquireQnaAnswerCl.visibility =
                    if (binding.itemInquireQnaAnswerCl.visibility == View.VISIBLE)
                        View.GONE
                    else
                        View.VISIBLE
            }
        }
    }
}
