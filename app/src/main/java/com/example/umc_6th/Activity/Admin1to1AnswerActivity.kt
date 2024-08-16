package com.example.umc_6th.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.umc_6th.MainActivity
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.Response.RootQNADeleteResponse
import com.example.umc_6th.Retrofit.Response.RootQNAViewResponse
import com.example.umc_6th.databinding.ActivityAdmin1to1AnswerBinding
import com.example.umc_6th.databinding.ActivityAdmin1to1Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Admin1to1AnswerActivity : AppCompatActivity(){
    lateinit var binding: ActivityAdmin1to1AnswerBinding

    private var qna_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdmin1to1AnswerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        qna_id = intent.getIntExtra("QnA_id", 0)

        binding.admin1to1CommentBackIv.setOnClickListener {
            finish()
        }
        callGetRootQNAView(qna_id)
        initSetOnClickListener()
    }
    private fun initSetOnClickListener() {
        binding.admin1to1AnswerRemoveTv.setOnClickListener {
            callDeleteRootQna(qna_id)
        }
        binding.admin1to1AnswerRemoveTv.setOnClickListener {
            //수정 알아서
        }
    }
    private fun callGetRootQNAView(qna_id : Int){
        CookieClient.service.getRootQNAView(MainActivity.accessToken, qna_id).enqueue(object :
            Callback<RootQNAViewResponse> {
            override fun onResponse(
                call: Call<RootQNAViewResponse>,
                response: Response<RootQNAViewResponse>
            ) {
                setImage(binding.admin1to1AnswerProfileIv, response.body()?.result!!.userprofile)
                binding.admin1to1AnswerNameTv.text = response.body()?.result!!.userNickname
                binding.admin1to1AnswerTimeTv.text = response.body()?.result!!.createdAt
                binding.admin1to1AnswerTitleTv.text = response.body()?.result!!.title
                binding.admin1to1AnswerBodyTv.text = response.body()?.result!!.content
                Log.d("rootqna",response.toString())
            }

            override fun onFailure(call: Call<RootQNAViewResponse>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }
        })
    }

    private fun callDeleteRootQna(qna_id: Int){
        CookieClient.service.deleteRootQna(MainActivity.accessToken, qna_id).enqueue(object :
        Callback<RootQNADeleteResponse> {
            override fun onFailure(call: Call<RootQNADeleteResponse>, t: Throwable) {
                Log.e("root_qna_Delete", t.toString())
            }

            override fun onResponse(
                call: Call<RootQNADeleteResponse>,
                response: Response<RootQNADeleteResponse>
            ) {
                Log.d("root_qna_Delete",response.toString())
                if (response.isSuccessful) {
                    if (response.body()?.isSuccess == true) {
                        Log.d("root_qna_Delete", "Board deleted successfully")
                    } else {
                        Log.e("root_qna_Delete", "Failed to delete board: ${response.body()?.message}")
                    }
                } else {
                    Log.e("root_qna_Delete", "Response error: ${response.errorBody()?.string()}")
                }
            }
        })
    }
    private fun setImage(view: ImageView, url:String) {
        Glide.with(this).load(url).into(view)
    }
}