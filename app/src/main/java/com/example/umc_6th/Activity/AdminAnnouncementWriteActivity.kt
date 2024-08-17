package com.example.umc_6th.Activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.Retrofit.RetrofitClient
import com.example.umc_6th.databinding.ActivityAdmin1to1Binding
import com.example.umc_6th.databinding.ActivityAdminAnnouncementWriteBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.umc_6th.Retrofit.Request.AnnouncementRegisterRequest


class AdminAnnouncementWriteActivity : AppCompatActivity(){
    lateinit var binding: ActivityAdminAnnouncementWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminAnnouncementWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.adminAnnouncemnetBackIv.setOnClickListener {
            finish()
        }

        //서버에 공지사항 제목과 내용 전송
        binding.adminQuestWritingBtnIv.setOnClickListener {
            val title = binding.adminQuestWritingTitleEt.text.toString()
            val content = binding.adminQuestWritingBodyEt.text.toString()

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "제목과 내용을 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sp = getSharedPreferences("Auth", MODE_PRIVATE)
            val accessToken = sp.getString("AccessToken", "").toString()

            val request = AnnouncementRegisterRequest(title, content)

            val call = RetrofitClient.service.postRootNoticeRegister(
                accessToken, request
            )

            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Log.d("AdminAnnouncementWriteActivity", "공지사항 등록 성공")
                        // 성공 처리 로직 추가
                        finish() // 액티비티 종료
                    } else {
                        Log.d("AdminAnnouncementWriteActivity", "공지사항 등록 실패: ${response.code()} , 에러메시지: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d("AdminAnnouncementWriteActivity", "공지사항 등록 에러: ${t.message}")
                }
            })


        }

    }
}