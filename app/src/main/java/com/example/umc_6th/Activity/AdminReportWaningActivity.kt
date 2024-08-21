package com.example.umc_6th.Activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.umc_6th.AdminWarnReasonActivity.Companion.adminWarnReasonActivity
import com.example.umc_6th.MainActivity
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.Request.warnRequest
import com.example.umc_6th.Retrofit.Response.WarnResponse
import com.example.umc_6th.databinding.ActivityAdminReportWarningBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminReportWaningActivity : AppCompatActivity() {

    lateinit var binding: ActivityAdminReportWarningBinding
    private var accessToken: String = MainActivity.accessToken

    companion object{
        var boardId: Int = 0
        var targetUserId: Int = 0
        var division: String = ""
        var reason: String = ""
        var nickname: String = ""
        var userPic: String = ""
        var boardTitle: String =""
        var boardContent: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminReportWarningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        division = intent.getStringExtra("division") ?: ""
        reason = intent.getStringExtra("reason") ?: ""

        binding.adminReportWarningTitleTv.text = boardTitle
        binding.adminReportWarningNameTv.text = nickname
        Glide.with(this)
            .load(userPic)
            .into(binding.adminReportWarningProfileIv)
        binding.adminReportWarningBodyTv.text = boardContent

        binding.adminReportWarningBackIv.setOnClickListener {
            finish()
        }

        binding.adminReportWarningBtnIv.setOnClickListener {
            sendWarning(accessToken, boardId, targetUserId, reason, division)

        }
    }

    private fun sendWarning(accessToken: String, boardId: Int, targetUserId: Int, message: String, division: String) {
        val warnRequest = warnRequest(
            boardId = boardId.toString(),
            targetUserId = targetUserId.toString(),
            message = message,
            division = division
        )
        Log.d("retrofit_warn", warnRequest.toString())

        CookieClient.service.postAdminWarn(accessToken, warnRequest).enqueue(object : Callback<WarnResponse> {
            override fun onResponse(call: Call<WarnResponse>, response: Response<WarnResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.isSuccess == true) {
                        Log.d("retrofit_warn", response.toString())
                        AdminReportCommentActivity.adminReportCommentActivity.finish()
                        AdminReportBoardActivity.adminReportBoardActivity.finish()
                        adminWarnReasonActivity.finish()
                        finish()
                    } else {
                        Log.e("retrofit_warn", "경고 부여 실패: ${body?.message}")
                    }
                } else {
                    Log.e("retrofit_warn", "응답 오류: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<WarnResponse>, t: Throwable) {
                Log.e("retrofit_warn", "네트워크 오류: ${t.message}")
            }
        })
    }
}
