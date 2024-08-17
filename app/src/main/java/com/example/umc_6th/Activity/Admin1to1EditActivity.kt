package com.example.umc_6th.Activity

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.umc_6th.MainActivity
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.Request.AnnouncementRegisterRequest
import com.example.umc_6th.Retrofit.Response.RootQNAViewResponse
import com.example.umc_6th.Retrofit.RetrofitClient
import com.example.umc_6th.databinding.ActivityAdmin1to1Binding
import com.example.umc_6th.databinding.ActivityAdmin1to1EditBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.umc_6th.Retrofit.Request.QNACommentRequest
import com.example.umc_6th.Retrofit.Request.QNACommentModifyRequest


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
        callGetRootQNAView(qna_id)

        //문의 답변 수정 요청 받았는지 확인
        val receivedText = intent.getStringExtra("contentText")
        if (receivedText != null) {
            binding.admin1to1CommentBtnTv.text = "답변 수정 완료"
        }

        //문의 답변 등록
        binding.admin1to1CommentEditEt.setOnClickListener {
            if(binding.admin1to1CommentBtnTv.text == "답변 수정 완료"){
                modifyCommentQNA()
            }
            else{
                postCommentQNA()
            }
        }
    }
    private fun callGetRootQNAView(qna_id : Int){
        CookieClient.service.getRootQNAView(MainActivity.accessToken, qna_id).enqueue(object :
            Callback<RootQNAViewResponse> {
            override fun onResponse(
                call: Call<RootQNAViewResponse>,
                response: Response<RootQNAViewResponse>
            ) {
                setImage(binding.admin1to1CommentProfileIv, response.body()?.result!!.userprofile)
                binding.admin1to1CommentNameTv.text = response.body()?.result!!.userNickname
                binding.admin1to1CommentTimeTv.text = response.body()?.result!!.createdAt
                binding.admin1to1CommentTitleTv.text = response.body()?.result!!.title
                binding.admin1to1CommentBodyTv.text = response.body()?.result!!.content
                Log.d("rootqna",response.toString())
            }

            override fun onFailure(call: Call<RootQNAViewResponse>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }
        })
    }
    private fun setImage(view: ImageView, url:String) {
        Glide.with(this).load(url).into(view)
    }

    private fun postCommentQNA(){
        val answer = binding.admin1to1CommentEditEt.text.toString()

        val sp = getSharedPreferences("Auth", MODE_PRIVATE)
        val accessToken = sp.getString("AccessToken", "").toString()

        val request = QNACommentRequest(answer)

        val call = RetrofitClient.service.postRootQNAReplyRegister(
            accessToken, qna_id, request
        )
        //나중에 qna_id 추출해야함.

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("Admin1to1EditActivity", "문의 답변 등록 성공")
                    // 성공 처리 로직 추가
                    finish() // 액티비티 종료
                } else {
                    Log.d("Admin1to1EditActivity", "문의 답변 등록 실패: ${response.code()} , 에러메시지: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("Admin1to1EditActivity", "문의 답변 등록 에러: ${t.message}")
            }
        })
    }
    private fun modifyCommentQNA(){
        val answer = binding.admin1to1CommentEditEt.text.toString()

        val sp = getSharedPreferences("Auth", MODE_PRIVATE)
        val accessToken = sp.getString("AccessToken", "").toString()

        val request = QNACommentModifyRequest(answer)

        val call = RetrofitClient.service.patchRootQNAReplyModify(
            accessToken, qna_id, request
        )
        //나중에 qna_id 추출해야함.

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("Admin1to1EditActivity", "문의 답변 수정 성공")
                    // 성공 처리 로직 추가
                    finish() // 액티비티 종료
                } else {
                    Log.d("Admin1to1EditActivity", "문의 답변 수정 실패: ${response.code()} , 에러메시지: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("Admin1to1EditActivity", "문의 답변 수정 에러: ${t.message}")
            }
        })
    }
}