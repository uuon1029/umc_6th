package com.example.umc_6th.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.DataClass.DialogAdminAnnouncemenetDelete
import com.example.umc_6th.MainActivity
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.NoticeDetailResponse
import com.example.umc_6th.Retrofit.NoticeListResponse
import com.example.umc_6th.Retrofit.RetrofitClient
import com.example.umc_6th.databinding.ActivityAdmin1to1Binding
import com.example.umc_6th.databinding.ActivityAdminAnnouncementWriteBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.umc_6th.Retrofit.Request.AnnouncementRegisterRequest
import com.example.umc_6th.Retrofit.Request.AnnouncementModifyRequest
import com.example.umc_6th.Retrofit.Response.RootNoticeResponse

class AdminAnnouncementWriteActivity : AppCompatActivity(){
    lateinit var binding: ActivityAdminAnnouncementWriteBinding
    var notice_id = 0 // 수정을 위한 notice id로 임시 저장해둔 것임.. 추출해와야함.
     //notice_id 알아와야함.
    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminAnnouncementWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notice_id = intent.getIntExtra("announcement_id",0)

        callGetAnnouncement()

        binding.adminAnnouncemnetBackIv.setOnClickListener {
            finish()
        }

        //공지사항 title, content intent로 전달 받기

        //서버에 공지사항 제목과 내용 전송
        binding.adminQuestWritingBtnIv.setOnClickListener {
            if(binding.adminQuestWritingBtnTv.text=="완료"){
                AnnouncemetRegister()
            }
            else{
                AnnouncementModify()
            }
        }

        binding.questRemoveIv.setOnClickListener {
            val deleteDialog = DialogAdminAnnouncemenetDelete(this)
            deleteDialog.setDialogClickListener(object : DialogAdminAnnouncemenetDelete.myDialogDoneClickListener{
                override fun done() {
                    CallDeleteRootNotice(notice_id)
                    finish()
                }
            })
            deleteDialog.show()
        }

    }

    private fun AnnouncemetRegister(){
        val title = binding.adminQuestWritingTitleEt.text.toString()
        val content = binding.adminQuestWritingBodyEt.text.toString()


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

    private fun AnnouncementModify() {
        val title = binding.adminQuestWritingTitleEt.text.toString()
        val content = binding.adminQuestWritingBodyEt.text.toString()

        val sp = getSharedPreferences("Auth", MODE_PRIVATE)
        val accessToken = sp.getString("AccessToken", "").toString()

        val request = AnnouncementModifyRequest(title, content)

        val call = RetrofitClient.service.patchRootNoticeModify(
            accessToken,
            notice_id, //임시로 0 지정해두었습니다 수정이 필요합니다.
            request
        )

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("AdminAnnouncementWriteActivity", "공지사항 수정 성공")
                    // 성공 처리 로직 추가
                    finish() // 액티비티 종료
                } else {
                    Log.d(
                        "AdminAnnouncementWriteActivity",
                        "공지사항 수정 실패: ${response.code()} , 에러메시지: ${response.message()}"
                    )
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("AdminAnnouncementWriteActivity", "공지사항 수정 에러: ${t.message}")
            }
        })
    }

    private fun CallDeleteRootNotice(notice_id : Int){
        CookieClient.service.deleteRootNotice(MainActivity.accessToken, notice_id).enqueue(object : Callback<RootNoticeResponse> {
            override fun onFailure(call: Call<RootNoticeResponse>, t: Throwable) {
                Log.e("AnnouncementDelete", t.toString())
            }

            override fun onResponse(
                call: Call<RootNoticeResponse>,
                response: Response<RootNoticeResponse>
            ) {
                Log.d("AnnouncementDelete",response.toString())
                if (response.isSuccessful) {
                    if (response.body()?.isSuccess == true) {
                        Log.d("AnnouncementDelete", "Board deleted successfully")
                    } else {
                        Log.e("AnnouncementDelete", "Failed to delete board: ${response.body()?.message}")
                    }
                } else {
                    Log.e("AnnouncementDelete", "Response error: ${response.errorBody()?.string()}")
                }
            }
        })
    }

    private fun callGetAnnouncement() {
        if(notice_id!=0){
            CookieClient.service.getAnnouncement(MainActivity.accessToken,notice_id).enqueue(object : Callback<NoticeDetailResponse> {
                override fun onFailure(call: Call<NoticeDetailResponse>?, t: Throwable?) {
                    Log.e("retrofit", t.toString())
                }

                override fun onResponse(
                    call: Call<NoticeDetailResponse>?,
                    response: Response<NoticeDetailResponse>?
                ) {
                    Log.d("retrofit",response.toString())
                    if( response?.body() != null){
                        binding.adminQuestWritingTitleEt.setText(response.body()!!.result.title)
                        binding.adminQuestWritingBodyEt.setText(response.body()!!.result.content)
                    }
                }
            })
        } else {
            binding.questRemoveIv.visibility = View.GONE
        }
    }
}