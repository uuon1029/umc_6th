package com.example.umc_6th.Activity

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.MainActivity
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.Request.SuspensionRequest
import com.example.umc_6th.Retrofit.Response.SuspendResponse
import com.example.umc_6th.databinding.ActivityAdminReportSuspensionBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminReportSuspensionActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminReportSuspensionBinding

    companion object{
        var suspensionDueInt: Int = 0
        var boardId: Int = 0
        var targetUserId: Int = 0
        var division: String = ""
        var reason: String = ""
        var accessToken: String = MainActivity.accessToken
    }

    var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminReportSuspensionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDayClickListener()

        binding.adminReportSuspensionBackIv.setOnClickListener {
            finish()
        }

        binding.adminReportSuspensionBtnIv.setOnClickListener {
            if (suspensionDueInt != 0){
                sendSuspensionRequest(accessToken, boardId, targetUserId, reason, division, suspensionDueInt)
            } else{
                Log.e("retrofit/Suspension", "정지 기간이 선택되지 않았습니다.")
            }
        }
    }

    private fun initDayClickListener() {
        binding.adminReportSuspension3dayTv.setOnClickListener {
            setSelectedTab(binding.adminReportSuspension3dayTv)
            suspensionDueInt = 3
        }

        binding.adminReportSuspension7dayTv.setOnClickListener {
            setSelectedTab(binding.adminReportSuspension7dayTv)
            suspensionDueInt = 7
        }

        binding.adminReportSuspension14dayTv.setOnClickListener {
            setSelectedTab(binding.adminReportSuspension14dayTv)
            suspensionDueInt = 14
        }

        binding.adminReportSuspension30dayTv.setOnClickListener {
            setSelectedTab(binding.adminReportSuspension30dayTv)
            suspensionDueInt = 30
        }

        binding.adminReportSuspension90dayTv.setOnClickListener {
            setSelectedTab(binding.adminReportSuspension90dayTv)
            suspensionDueInt = 90
        }

        binding.adminReportSuspensionForeverTv.setOnClickListener {
            setSelectedTab(binding.adminReportSuspensionForeverTv)
            suspensionDueInt = 99999  // 뭐로 해야하지
        }
    }

    private fun setSelectedTab(selectedText: TextView) {
        resetTabSelections()
        selectedText.isSelected = true
    }

    private fun resetTabSelections() {
        binding.adminReportSuspension3dayTv.isSelected = false
        binding.adminReportSuspension7dayTv.isSelected = false
        binding.adminReportSuspension14dayTv.isSelected = false
        binding.adminReportSuspension30dayTv.isSelected = false
        binding.adminReportSuspension90dayTv.isSelected = false
        binding.adminReportSuspensionForeverTv.isSelected = false
    }

    private fun sendSuspensionRequest(
        accessToken: String,
        boardId: Int,
        targetUserId: Int,
        message: String,
        division: String,
        suspensionDueInt: Int
    ) {
        val suspensionRequest = SuspensionRequest(
            boardId = boardId.toString(),
            targetUserId = targetUserId,
            message = message,
            division = division,
            suspensionDueInt = suspensionDueInt
        )

        CookieClient.service.postAdminSuspension(accessToken, suspensionRequest).enqueue(object : Callback<SuspendResponse> {
            override fun onResponse(call: Call<SuspendResponse>, response: Response<SuspendResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.isSuccess == true) {
                        Log.d("retrofit_suspension", response.toString())

                    } else {
                        Log.e("retrofit_suspension", "정지 요청 실패: ${body?.message}")
                    }
                } else {
                    Log.e("retrofit_suspension", "응답 오류: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<SuspendResponse>, t: Throwable) {
                Log.e("retrofit_suspension", "네트워크 오류: ${t.message}")
            }
        })
    }
}
