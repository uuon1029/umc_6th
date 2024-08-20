package com.example.umc_6th

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_6th.Activity.ReportActivity
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.DataClass.PinComment
import com.example.umc_6th.Retrofit.Response.CommentDeleteResponse
import com.example.umc_6th.Retrofit.Response.CommentLikeReponse
import com.example.umc_6th.databinding.ItemQuestSubAnswerBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubAnswerRVAdapter(): RecyclerView.Adapter<SubAnswerRVAdapter.Holder>() {

    var context: Context? = null
    //private val activity: QuestActivity, // QuestActivity 인스턴스 전달
    var itemList : ArrayList<PinComment> = ArrayList<PinComment>()
    var itemClickListener: MainAnswerRVAdapter.OnItemClickListener? = null

    companion object{
        var openedMineLayout: View? = null
        var openedYourLayout: View? = null
    }

    fun interface MyOnClickListener{
        fun initStatus()
    }
    lateinit var myOnClickListener: MyOnClickListener
    fun setClickListener(itemClickListener: MyOnClickListener){
        myOnClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemQuestSubAnswerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding,context, itemClickListener)
    }

    override fun onBindViewHolder(holder: SubAnswerRVAdapter.Holder, position: Int) {
        val item = itemList[position]
        holder.bind(item)

        // 초기 상태 설정
        holder.binding.itemQuestSubAnswerMineCl.visibility = View.GONE
        holder.binding.itemQuestSubAnswerYourCl.visibility = View.GONE

        // 더보기 버튼 클릭 리스너 설정
        holder.binding.itemQuestSubAnswerMoreIv.setOnClickListener {
            val isMine = item.userId == MainActivity.userId
            if (isMine) {
                openedMineLayout = holder.binding.itemQuestSubAnswerMineCl
                Log.d("test","mine")
                holder.binding.itemQuestSubAnswerMineCl.visibility =
                    if (holder.binding.itemQuestSubAnswerMineCl.visibility == View.VISIBLE) View.GONE else View.VISIBLE
                holder.binding.itemQuestSubAnswerYourCl.visibility = View.GONE
            } else {
                openedYourLayout = holder.binding.itemQuestSubAnswerYourCl
                holder.binding.itemQuestSubAnswerYourCl.visibility =
                    if (holder.binding.itemQuestSubAnswerYourCl.visibility == View.VISIBLE) View.GONE else View.VISIBLE
                holder.binding.itemQuestSubAnswerMineCl.visibility = View.GONE
            }
        }


        holder.binding.itemQuestSubAnswerDeleteCl.setOnClickListener {
            Log.d("retrofit/Comment_del",item.id.toString())
            CookieClient.service.deleteComment(MainActivity.accessToken, item.id).enqueue(object : Callback<CommentDeleteResponse> {
                override fun onResponse(call: Call<CommentDeleteResponse>, response: Response<CommentDeleteResponse>) {
                    if (response.isSuccessful) {
                        Log.d("retrofit/Comment_del",response.body().toString())
                        itemClickListener!!.initRV()
                    } else {
                        Log.d("DeleteError", "Failed to delete comment: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<CommentDeleteResponse>, t: Throwable) {
                    Log.e("DeleteError", "Failed to delete comment", t)
                }
            })
        }

        holder.binding.itemQuestSubAnswerEditCl.setOnClickListener {
            if(item.pinPicList != null){
                itemClickListener!!.onEditCommentClick(item.id, item.comment, item.pinPicList)
            }
            else{
                itemClickListener!!.onEditnopictureCommentClick(item.id, item.comment)
            }

        }

        holder.binding.itemQuestSubAnswerYourCl.setOnClickListener {
            val intent = Intent(context,ReportActivity::class.java)
            intent.putExtra("comment_id", item.id)
            context!!.startActivity(intent)
        }

        holder.binding.itemQuestSubAnswerLikeIv.setOnClickListener {
            Log.d("retrofit/Comment_like",item.id.toString())
            CookieClient.service.postCommentLike(MainActivity.accessToken, item.id).enqueue(object :
                Callback<CommentLikeReponse> {
                override fun onFailure(call: Call<CommentLikeReponse>, t: Throwable) {
                    Log.e("retrofit_error", t.toString())
                }

                override fun onResponse(
                    call: Call<CommentLikeReponse>,
                    response: Response<CommentLikeReponse>
                ) {
                    Log.d("retrofit_pin", response.body()!!.code)
                    myOnClickListener.initStatus()
                }
            })
        }
        holder.binding.itemQuestSubAnswerUnlikeIv.setOnClickListener {
            Log.d("retrofit/Comment_like",item.id.toString())
            CookieClient.service.postCommentLike(MainActivity.accessToken, item.id).enqueue(object :
                Callback<CommentLikeReponse> {
                override fun onFailure(call: Call<CommentLikeReponse>, t: Throwable) {
                    Log.e("retrofit_error", t.toString())
                }

                override fun onResponse(
                    call: Call<CommentLikeReponse>,
                    response: Response<CommentLikeReponse>
                ) {
                    Log.d("retrofit_pin", response.body()!!.code)
                    myOnClickListener.initStatus()
                }
            })
        }
    }

    override fun getItemCount(): Int = itemList.size

    inner class Holder(val binding: ItemQuestSubAnswerBinding,val context: Context?, private val itemClickListener: MainAnswerRVAdapter.OnItemClickListener?) : RecyclerView.ViewHolder(binding.root){

        private fun setImage(view: ImageView, url:String) {
            if (context != null){
                Glide.with(context).load(url).into(view)
            }
        }
        fun bind(item: PinComment){
            if(itemList != null) {
                binding.itemQuestSubAnswerNameTv.text = item.userNickname
                binding.itemQuestSubAnswerTimeTv.text = item.pinCommentDate
                binding.itemQuestSubAnswerBodyTv.text = item.comment
                binding.itemQuestSubAnswerLikenumIv.text = item.pinLikeCount.toString()

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

                binding.itemQuestSubAnswerImg1Iv.visibility = View.GONE
                binding.itemQuestSubAnswerImg2Iv.visibility = View.GONE
                binding.itemQuestSubAnswerImg3Iv.visibility = View.GONE

                if(item.pinPicList != null) {
                    val imgList = item.pinPicList
                    val size: Int = imgList.size

                    if (size > 0) {
                        setImage(binding.itemQuestSubAnswerImg1Iv, imgList[0])
                        binding.itemQuestSubAnswerImg1Iv.visibility = View.VISIBLE
                    }
                    if (size > 1) {
                        setImage(binding.itemQuestSubAnswerImg2Iv, imgList[1])
                        binding.itemQuestSubAnswerImg2Iv.visibility = View.VISIBLE
                    }
                    if (size > 2) {
                        setImage(binding.itemQuestSubAnswerImg3Iv, imgList[2])
                        binding.itemQuestSubAnswerImg3Iv.visibility = View.VISIBLE
                    }
                }

                binding.itemQuestSubAnswerProfileIv.setOnClickListener {
                    itemClickListener!!.onSubProfileImageClick(adapterPosition)
                }
            }
        }
    }

}