package com.example.umc_6th

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_6th.Activity.ReportActivity
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.DataClass.Pin
import com.example.umc_6th.Retrofit.DataClass.PinComment
import com.example.umc_6th.Retrofit.Request.BoardReportRequest
import com.example.umc_6th.Retrofit.Response.BoardReportResponse
import com.example.umc_6th.Retrofit.Response.CommentDeleteResponse
import com.example.umc_6th.Retrofit.Response.CommentLikeReponse
import com.example.umc_6th.Retrofit.Response.CommentReportResponse
import com.example.umc_6th.databinding.ItemQuestMainAnswerBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainAnswerRVAdapter(private val context: Context, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<MainAnswerRVAdapter.Holder>() {
    var itemList = ArrayList<Pin>()
    private var openedMineLayout: View? = null
    private var openedYourLayout: View? = null

    interface OnItemClickListener {
        fun onProfileImageClick(userId: Int)
        fun onSubProfileImageClick(position: Int)
        fun onCommentDeleteClick(pinId: Int, userId: Int)
        fun onUnchatClick(pinId: Pin) // 대댓글 등록 처리 위한 이벤트
        fun onEditCommentClick(comment: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemQuestMainAnswerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, context, itemClickListener )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        holder.bind(item)

        // 초기 상태 설정
        holder.binding.itemQuestMainAnswerMineCl.visibility = View.GONE
        holder.binding.itemQuestMainAnswerYourCl.visibility = View.GONE

        // 더보기 버튼 클릭 리스너 설정
        holder.binding.itemQuestMainAnwserMoreIv.setOnClickListener {
            val isMine = item.userId == MainActivity.userId
            if (isMine) {
                // 이미 열려 있던 레이아웃이 있으면 닫기
                openedMineLayout?.let {
                    it.visibility = View.GONE
                }

                // 현재 레이아웃을 열거나 닫기
                holder.binding.itemQuestMainAnswerMineCl.visibility =
                    if (holder.binding.itemQuestMainAnswerMineCl.visibility == View.VISIBLE) View.GONE else View.VISIBLE

                // 새로 열린 레이아웃 추적
                if (holder.binding.itemQuestMainAnswerMineCl.visibility == View.VISIBLE) {
                    openedMineLayout = holder.binding.itemQuestMainAnswerMineCl
                }
                holder.binding.itemQuestMainAnswerYourCl.visibility = View.GONE
            } else {
                // 이미 열려 있던 레이아웃이 있으면 닫기
                openedYourLayout?.let {
                    it.visibility = View.GONE
                }

                // 현재 레이아웃을 열거나 닫기
                holder.binding.itemQuestMainAnswerYourCl.visibility =
                    if (holder.binding.itemQuestMainAnswerYourCl.visibility == View.VISIBLE) View.GONE else View.VISIBLE

                // 새로 열린 레이아웃 추적
                if (holder.binding.itemQuestMainAnswerYourCl.visibility == View.VISIBLE) {
                    openedYourLayout = holder.binding.itemQuestMainAnswerYourCl
                }
                holder.binding.itemQuestMainAnswerMineCl.visibility = View.GONE
            }
        }


        holder.binding.itemQuestMainAnswerDeleteCl.setOnClickListener {
            CookieClient.service.deletePin(MainActivity.accessToken, item.id).enqueue(object : Callback<CommentDeleteResponse> {
                override fun onResponse(call: Call<CommentDeleteResponse>, response: Response<CommentDeleteResponse>) {
                    if (response.isSuccessful) {
                        itemClickListener.onCommentDeleteClick(item.id, item.userId)
                        itemList.removeAt(holder.bindingAdapterPosition)
                        notifyItemRemoved(holder.bindingAdapterPosition)
                    } else {
                        Log.e("DeleteError", "Failed to delete comment: ${response.code()}")
                    }
                }
                override fun onFailure(call: Call<CommentDeleteResponse>, t: Throwable) {
                    Log.e("DeleteError", "Failed to delete comment", t)
                }
            })
        }

        holder.binding.itemQuestMainAnswerEditCl.setOnClickListener {
            // 수정하기
            itemClickListener.onEditCommentClick(item.comment)
        }

        holder.binding.itemQuestMainAnswerYourCl.setOnClickListener {
            holder.binding.itemQuestMainAnswerYourCl.visibility = View.GONE
            val intent = Intent(context, ReportActivity::class.java)
            intent.putExtra("pin_id", item.id)
            context.startActivity(intent)
        }

        holder.binding.itemQuestMainAnwserLikeIv.setOnClickListener {
            CookieClient.service.postPinLike(MainActivity.accessToken, item.id).enqueue(object :
                Callback<CommentLikeReponse> {
                override fun onFailure(call: Call<CommentLikeReponse>, t: Throwable) {
                    Log.e("retrofit_error", t.toString())
                }

                override fun onResponse(
                    call: Call<CommentLikeReponse>,
                    response: Response<CommentLikeReponse>
                ) {
                    if (response.body()?.code == "LIKE200") {
                        holder.binding.itemQuestMainAnwserLikeIv.visibility = View.VISIBLE
                        holder.binding.itemQuestMainAnwserUnlikeIv.visibility = View.GONE
                        Log.d("retrofit_pin", response.body()!!.code)
                        holder.binding.itemQuestMainAnwserLikenumIv.text =
                            (holder.binding.itemQuestMainAnwserLikenumIv.text.toString()
                                .toInt() + 1).toString()
                    }
                    if (response.body()?.code == "LIKE201") {
                        holder.binding.itemQuestMainAnwserLikeIv.visibility = View.GONE
                        holder.binding.itemQuestMainAnwserUnlikeIv.visibility = View.VISIBLE
                        Log.d("retrofit_pin", response.body()!!.code)
                        holder.binding.itemQuestMainAnwserLikenumIv.text =
                            (holder.binding.itemQuestMainAnwserLikenumIv.text.toString()
                                .toInt() - 1).toString()
                    }
                }
            })
        }
        holder.binding.itemQuestMainAnwserUnlikeIv.setOnClickListener {
            Log.d("retrofit_pin_like", "test")
            CookieClient.service.postPinLike(MainActivity.accessToken, item.id).enqueue(object :
                Callback<CommentLikeReponse> {
                override fun onFailure(call: Call<CommentLikeReponse>, t: Throwable) {
                    Log.e("retrofit_error", t.toString())
                }

                override fun onResponse(
                    call: Call<CommentLikeReponse>,
                    response: Response<CommentLikeReponse>
                ) {
                    if (response.body()?.code == "LIKE201") {
                        holder.binding.itemQuestMainAnwserLikeIv.visibility = View.GONE
                        holder.binding.itemQuestMainAnwserUnlikeIv.visibility = View.VISIBLE
                        Log.d("retrofit_pin", response.body()!!.code)
                        holder.binding.itemQuestMainAnwserLikenumIv.text =
                            (holder.binding.itemQuestMainAnwserLikenumIv.text.toString()
                                .toInt() - 1).toString()
                    }
                    if (response.body()?.code == "LIKE200") {
                        holder.binding.itemQuestMainAnwserLikeIv.visibility = View.VISIBLE
                        holder.binding.itemQuestMainAnwserUnlikeIv.visibility = View.GONE
                        Log.d("retrofit_pin", response.body()!!.code)
                        holder.binding.itemQuestMainAnwserLikenumIv.text =
                            (holder.binding.itemQuestMainAnwserLikenumIv.text.toString()
                                .toInt() + 1).toString()
                    }
                }
            })

        }

    }

    override fun getItemCount(): Int = itemList.size

    fun closeAllOpenLayouts() {
        if(openedMineLayout?.isPressed == true ||openedYourLayout?.isPressed==true){
            Log.d("test","1")
        }else{
            openedMineLayout?.visibility = View.GONE
            openedYourLayout?.visibility = View.GONE
            if(SubAnswerRVAdapter.openedMineLayout?.isPressed==true||SubAnswerRVAdapter.openedYourLayout?.isPressed==true){
                Log.d("test","2")
            }else{
                SubAnswerRVAdapter.openedMineLayout?.visibility = View.GONE
                SubAnswerRVAdapter.openedYourLayout?.visibility = View.GONE
            }
        }
    }

    inner class Holder(
        val binding: ItemQuestMainAnswerBinding,
        private val context: Context,
        val itemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        val subAnswerAdapter = SubAnswerRVAdapter()

        private fun setImage(view: ImageView,url:String) {
            Glide.with(context).load(url).into(view)
        }
        fun bind(item: Pin) {

            binding.itemQuestMainAnwserNameTv.text = item.userNickname
            binding.itemQuestMainAnwserBodyTv.text = item.comment
            binding.itemQuestMainAnwserTimeTv.text = item.pinDate
//            setImage(binding.itemQuestMainAnwserProfileIv, item.)

            when(item.isLiked){
                true -> {
                    binding.itemQuestMainAnwserUnlikeIv.visibility = View.GONE
                    binding.itemQuestMainAnwserLikeIv.visibility = View.VISIBLE
                }
                false -> {
                    binding.itemQuestMainAnwserUnlikeIv.visibility = View.VISIBLE
                    binding.itemQuestMainAnwserLikeIv.visibility = View.GONE
                }
            }

            val imgList = item.pinPictureList
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

            subAnswerAdapter.context = context
            subAnswerAdapter.itemList = item.pinCommentList
            subAnswerAdapter.itemClickListener = itemClickListener
            binding.itemQuestMainAnwserSubRv.adapter = subAnswerAdapter
            binding.itemQuestMainAnwserSubRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            binding.itemQuestMainAnwserProfileIv.setOnClickListener {
                itemClickListener.onProfileImageClick(item.userId)
            }

            binding.itemQuestMainAnwserUnchatIv.setOnClickListener {
                itemClickListener.onUnchatClick(item)
            }
        }
    }
}