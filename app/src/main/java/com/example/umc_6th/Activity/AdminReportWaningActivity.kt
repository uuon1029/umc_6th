package com.example.umc_6th.Activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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

    private var boardId: Int = 0
    private var userId: Int = 0
    private var division: String = ""
    private var reason: String = ""
    private var accessToken: String = MainActivity.accessToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminReportWarningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        boardId = intent.getIntExtra("board_id", 0)
        userId = intent.getIntExtra("target_user_id", 0)
        division = intent.getStringExtra("division") ?: ""
        reason = intent.getStringExtra("reason") ?: ""

        binding.adminReportWarningBackIv.setOnClickListener {
            finish()
        }

        binding.adminReportWarningBtnIv.setOnClickListener {
            sendWarning(accessToken, boardId, userId, reason, division)
        }
    }

    private fun sendWarning(accessToken: String, boardId: Int, targetUserId: Int, message: String, division: String) {
        val warnRequest = warnRequest(
            boardId = boardId.toString(),
            targetUserId = targetUserId.toString(),
            message = message,
            division = division
        )

        CookieClient.service.postAdminWarn(accessToken, warnRequest).enqueue(object : Callback<WarnResponse> {
            override fun onResponse(call: Call<WarnResponse>, response: Response<WarnResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.isSuccess == true) {
                        Log.d("retrofit_admin_warn", response.toString())
                        // 성공 처리 후 다음 액티비티로 이동하거나 완료 메시지를 표시
                    } else {
                        Log.e("retrofit_admin_warn", "경고 부여 실패: ${body?.message}")
                    }
                } else {
                    Log.e("retrofit_admin_warn", "응답 오류: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<WarnResponse>, t: Throwable) {
                Log.e("retrofit_admin_warn", "네트워크 오류: ${t.message}")
            }
        })
    }
}
