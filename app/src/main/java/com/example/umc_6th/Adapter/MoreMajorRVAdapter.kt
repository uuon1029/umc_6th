package com.example.umc_6th

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_6th.Retrofit.DataClass.Board
import com.example.umc_6th.databinding.ItemMoreBinding
import org.json.JSONArray

class MoreMajorRVAdapter() : RecyclerView.Adapter<MoreMajorRVAdapter.ViewHolder>() {
    var moreMajorlist = ArrayList<Board>()

    fun interface MyItemClickListener{
        fun onItemClick(board: Board)

    }
    private lateinit var myItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        myItemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMoreBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(moreMajorlist[position])
        holder.itemView.setOnClickListener {
            myItemClickListener.onItemClick(moreMajorlist[position])
        }
    }
    override fun getItemCount(): Int  = moreMajorlist.size

    inner class ViewHolder(private val binding: ItemMoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(board: Board) {
            binding.itemMoreTitleTv.text = board.title
            binding.itemMoreBodyTv.text = board.content
            binding.itemMoreTimeTv.text = board.boardDate
            binding.itemMoreChatnumTv.text = board.pinCount.toString()
            binding.itemMoreLikenumTv.text = board.likeCount.toString()
//            if (board.picPreview != null) {
//                binding.itemMoreImgIv.setImageResource(board.picPreview.toInt())
//            }

//            val imageUrls = convertPicPreviewToList(board.picPreview)
//
//            if (imageUrls.isNotEmpty()) {
//                val imageUrl = "https://yesol.githyeon.shop/${imageUrls[0]}"
//                Glide.with(binding.itemMoreImgIv.context)
//                    .load(imageUrl) // 여기서 imageUrl을 사용
//                    .placeholder(R.drawable.ic_rectangle_gray_60) // 기본 이미지 설정
//                    .into(binding.itemMoreImgIv)
//            } else {
//                binding.itemMoreImgIv.setImageResource(R.drawable.ic_rectangle_gray_60) // 기본 이미지 설정
//            }
//
//            binding.itemMoreNameTv.text = board.userNickName
//        }
//
//        private fun convertPicPreviewToList(picPreview: String?): ArrayList<String> {
//            val list = ArrayList<String>()
//            if (!picPreview.isNullOrEmpty()) {
//                if (picPreview.startsWith("[")) { // JSONArray 형태인지 확인
//                    val jsonArray = JSONArray(picPreview)
//                    for (i in 0 until jsonArray.length()) {
//                        list.add(jsonArray.getString(i))
//                    }
//                } else {
//                    list.add(picPreview)
//                }
//            }
//            return list
        }
    }
}