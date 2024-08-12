package com.example.umc_6th.Activity

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.MainActivity
import com.example.umc_6th.R
import com.example.umc_6th.ReportCompleteActivity
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.Request.BoardReportRequest
import com.example.umc_6th.Retrofit.Response.BoardDeleteResponse
import com.example.umc_6th.Retrofit.Response.BoardReportResponse
import com.example.umc_6th.databinding.ActivityReportBinding
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class ReportActivity : AppCompatActivity() {

    lateinit var binding: ActivityReportBinding
    private var selectedReason: String? = null
    private var boardId: Int = 0
    var accessToken: String = MainActivity.accessToken

    companion object{
        var reportActivity : ReportActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        boardId = intent.getIntExtra("board_id", 0)
//
//        val spf = getSharedPreferences("Auth", MODE_PRIVATE)
//        accessToken = spf.getString("accessToken", "").toString()


        Log.d("ReportActivity", "Received boardId: $boardId")

        initClickListener()

        var etc = false

        reportActivity = this

        binding.etcButton.setOnClickListener {
            etc = if (etc) {
                binding.etcEt.visibility = View.INVISIBLE
                false
            } else {
                binding.etcEt.visibility = View.VISIBLE
                true
            }
        }

        binding.reportButton.setOnClickListener {
            sendReport(accessToken, boardId)
        }

        binding.reportBackTv.setOnClickListener {
            finish()
        }
    }

    private fun initClickListener() {
        val reasonViews = listOf<TextView>(
            binding.reportReason1,
            binding.reportReason2,
            binding.reportReason3,
            binding.reportReason4,
            binding.reportReason5,
            binding.reportReason6,
            binding.reportReason7,
            binding.reportReason8
        )

        for (reasonView in reasonViews) {
            reasonView.setOnClickListener {
                selectedReason = reasonView.text.toString()
                Log.d("ReportActivity", "선택된 이유: $selectedReason")

                for (view in reasonViews) {
                    view.setTextColor(resources.getColor(R.color.gray40))
                    view.setTypeface(null, Typeface.NORMAL)
                }

                reasonView.setTextColor(resources.getColor(R.color.black))
                reasonView.setTypeface(null, Typeface.BOLD)

                if (reasonView == binding.reportReason8) {
                    binding.etcEtLayout.visibility = View.VISIBLE
                } else {
                    binding.etcEtLayout.visibility = View.INVISIBLE
                }
            }
        }

        binding.reportButton.setOnClickListener {
            if (binding.etcEtLayout.visibility == View.VISIBLE) {
                selectedReason = binding.etcEt.text.toString()
                Log.d("ReportActivity", "ETC 사유: $selectedReason")
            }

            sendReport(accessToken, boardId)
        }

        binding.reportBackTv.setOnClickListener {
            finish()
        }
    }

    private fun sendReport(accessToken: String, boardId: Int) {
        if (!selectedReason.isNullOrEmpty()) {
            val reportRequest = BoardReportRequest(selectedReason!!)
            Log.d("ReportActivity", "이유: $selectedReason")
            CookieClient.service.postBoardReport(accessToken, boardId, reportRequest)
                .enqueue(object : Callback<BoardReportResponse> {
                    override fun onResponse(
                        call: Call<BoardReportResponse>,
                        response: Response<BoardReportResponse>
                    ) {
                        Log.d("retrofit", response.toString())
                        if (response.isSuccessful) {
                            if (response.body()?.code == "COMMON200") {
                                val intent = Intent(this@ReportActivity, ReportCompleteActivity::class.java)
                                startActivity(intent)
                                Log.d("report", "게시물이 성공적으로 신고되었습니다.")
                            } else {
                                Log.e(
                                    "report", "게시물 신고 실패: ${response.body()?.message}"
                                )
                            }
                        } else {
                            Log.e("report", "응답 오류: ${response.errorBody()?.string()}")
                        }
                    }
                    override fun onFailure(call: Call<BoardReportResponse>, t: Throwable) {
                        Log.e("report", "네트워크 오류: ${t.message}")
                    }
                })
        } else {
            Log.e("report", "보고서에 선택된 이유가 없습니다.")
        }
    }

}