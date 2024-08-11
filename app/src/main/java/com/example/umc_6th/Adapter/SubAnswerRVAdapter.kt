package com.example.umc_6th

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.DataClass.PinComment
import com.example.umc_6th.Retrofit.Response.CommentLikeReponse
import com.example.umc_6th.databinding.ItemQuestSubAnswerBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubAnswerRVAdapter(private val context: Context, private val itemList : ArrayList<PinComment>, private val itemClickListener: MainAnswerRVAdapter.OnItemClickListener): RecyclerView.Adapter<SubAnswerRVAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemQuestSubAnswerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding,context, itemClickListener)
    }

    override fun onBindViewHolder(holder: SubAnswerRVAdapter.Holder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
        holder.binding.itemQuestSubAnswerLikeIv.setOnClickListener {
            CookieClient.service.postCommentLike(MainActivity.accessToken, item.id).enqueue(object :
                Callback<CommentLikeReponse> {
                override fun onFailure(call: Call<CommentLikeReponse>, t: Throwable) {
                    Log.e("retrofit_error", t.toString())
                }

                override fun onResponse(
                    call: Call<CommentLikeReponse>,
                    response: Response<CommentLikeReponse>
                ) {
                    if(response.body()?.code == "LIKE200") {
                        holder.binding.itemQuestSubAnswerLikeIv.visibility = View.VISIBLE
                        holder.binding.itemQuestSubAnswerUnlikeIv.visibility = View.GONE
                        Log.d("retrofit_pin", response.body()!!.code)
                        holder.binding.itemQuestSubAnswerLikenumIv.text =
                            (holder.binding.itemQuestSubAnswerLikenumIv.text.toString().toInt()+1).toString()
                    }
                    if(response.body()?.code == "LIKE201") {
                        holder.binding.itemQuestSubAnswerLikeIv.visibility = View.GONE
                        holder.binding.itemQuestSubAnswerUnlikeIv.visibility = View.VISIBLE
                        Log.d("retrofit_pin", response.body()!!.code)
                        holder.binding.itemQuestSubAnswerLikenumIv.text =
                            (holder.binding.itemQuestSubAnswerLikenumIv.text.toString().toInt()-1).toString()
                    }
                }
            })
        }
        holder.binding.itemQuestSubAnswerUnlikeIv.setOnClickListener {
            Log.d("retrofit_pin_like","test")
            CookieClient.service.postPinLike(MainActivity.accessToken, item.id).enqueue(object :
                Callback<CommentLikeReponse> {
                override fun onFailure(call: Call<CommentLikeReponse>, t: Throwable) {
                    Log.e("retrofit_error", t.toString())
                }

                override fun onResponse(
                    call: Call<CommentLikeReponse>,
                    response: Response<CommentLikeReponse>
                ) {
                    if(response.body()?.code == "LIKE201") {
                        holder.binding.itemQuestSubAnswerLikeIv.visibility = View.GONE
                        holder.binding.itemQuestSubAnswerUnlikeIv.visibility = View.VISIBLE
                        Log.d("retrofit_pin", response.body()!!.code)
                        holder.binding.itemQuestSubAnswerLikenumIv.text =
                            (holder.binding.itemQuestSubAnswerLikenumIv.text.toString().toInt()-1).toString()
                    }
                    if(response.body()?.code == "LIKE200") {
                        holder.binding.itemQuestSubAnswerLikeIv.visibility = View.VISIBLE
                        holder.binding.itemQuestSubAnswerUnlikeIv.visibility = View.GONE
                        Log.d("retrofit_pin", response.body()!!.code)
                        holder.binding.itemQuestSubAnswerLikenumIv.text =
                            (holder.binding.itemQuestSubAnswerLikenumIv.text.toString().toInt()+1).toString()
                    }
                }
            })
        }
        holder.binding.itemQuestSubAnswerDeleteCl.setOnClickListener {
            val pinId = item.id
            val userId = item.userId
            itemClickListener.onCommentDeleteClick(pinId, userId)
        }
    }

    override fun getItemCount(): Int = itemList.size

    inner class Holder(val binding: ItemQuestSubAnswerBinding,private val context: Context, private val itemClickListener: MainAnswerRVAdapter.OnItemClickListener) : RecyclerView.ViewHolder(binding.root){

        private fun setImage(view: ImageView, url:String) {
            Glide.with(QuestActivity()).load(url).into(view)
        }
        fun bind(item: PinComment){
            binding.itemQuestSubAnswerNameTv.text = item.userNickname
            binding.itemQuestSubAnswerTimeTv.text = item.pinCommentDate
            binding.itemQuestSubAnswerBodyTv.text = item.comment

            when(item.isLiked){
                true -> {
                    binding.itemQuestSubAnswerUnlikeIv.visibility = View.GONE
                    binding.itemQuestSubAnswerLikeIv.visibility = View.VISIBLE
                }
                false -> {
                    binding.itemQuestSubAnswerUnlikeIv.visibility = View.VISIBLE
                    binding.itemQuestSubAnswerLikeIv.visibility = View.GONE
                }
            }

            if(item.pinPicList != null) {
                val imgList = item.pinPicList
                val size: Int = imgList.size
                when (size) {
                    1 -> {
                        setImage(binding.itemQuestMainAnswerImg1Iv, imgList[0])
                    }
                    2 -> {
                        setImage(binding.itemQuestMainAnswerImg1Iv, imgList[0])
                        setImage(binding.itemQuestMainAnswerImg2Iv, imgList[1])
                    }
                    3 -> {
                        setImage(binding.itemQuestMainAnswerImg1Iv, imgList[0])
                        setImage(binding.itemQuestMainAnswerImg2Iv, imgList[1])
                        setImage(binding.itemQuestMainAnswerImg3Iv, imgList[2])
                    }
                }

                binding.itemQuestMainAnswerImg1Iv.visibility = if (size > 0) View.VISIBLE else View.GONE
                binding.itemQuestMainAnswerImg2Iv.visibility = if (size > 1) View.VISIBLE else View.GONE
                binding.itemQuestMainAnswerImg3Iv.visibility = if (size > 2) View.VISIBLE else View.GONE


            }

            binding.itemQuestSubAnswerProfileIv.setOnClickListener {
                itemClickListener.onSubProfileImageClick(adapterPosition)
            }
        }
    }

}