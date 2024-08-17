package com.example.umc_6th.Activity

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.umc_6th.MainActivity
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.Response.RootQNAViewResponse
import com.example.umc_6th.databinding.ActivityAdmin1to1Binding
import com.example.umc_6th.databinding.ActivityAdmin1to1EditBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Admin1to1EditActivity : AppCompatActivity(){
    lateinit var binding: ActivityAdmin1to1EditBinding

    private var qna_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdmin1to1EditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        qna_id = intent.getIntExtra("QnA_id", 0)

        binding.admin1to1CommentBackIv.setOnClickListener {
            finish()
        }

        binding.admin1to1CommentBtnIv.setOnClickListener {
            finish()
        }

        callGetRootQNAView(qna_id)
    }

    private fun callGetRootQNAView(qna_id : Int){
        Log.d("retrofit","callGetRootQNAView")
        CookieClient.service.getRootQNAView(MainActivity.accessToken, qna_id).enqueue(object :
            Callback<RootQNAViewResponse> {
            override fun onResponse(
                call: Call<RootQNAViewResponse>,
                response: Response<RootQNAViewResponse>
            ) {
                Log.d("retrofit","요청은 감")
                if(response.body() != null){
                    setImage(binding.admin1to1CommentProfileIv, response.body()?.result!!.userprofile)
                    binding.admin1to1CommentNameTv.text = response.body()?.result!!.userNickname
                    binding.admin1to1CommentTimeTv.text = response.body()?.result!!.createdAt
                    binding.admin1to1CommentTitleTv.text = response.body()?.result!!.title
                    binding.admin1to1CommentBodyTv.text = response.body()?.result!!.content
                    Log.d("rootqna",response.toString())
                }
            }

            override fun onFailure(call: Call<RootQNAViewResponse>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }
        })
    }
    private fun setImage(view: ImageView, url:String) {
        Glide.with(this).load(url).into(view)
    }
}