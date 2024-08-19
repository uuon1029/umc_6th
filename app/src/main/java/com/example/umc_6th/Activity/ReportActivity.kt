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
import com.example.umc_6th.Retrofit.Request.CommentReportRequest
import com.example.umc_6th.Retrofit.Response.BoardReportResponse
import com.example.umc_6th.Retrofit.Response.CommentReportResponse
import com.example.umc_6th.databinding.ActivityReportBinding
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
class ReportActivity : AppCompatActivity() {

    lateinit var binding: ActivityReportBinding
    private var selectedReason: String? = null
    private var boardId: Int = 0
    private var pinId: Int = 0
    private var commentId: Int = 0

    var accessToken: String = MainActivity.accessToken

    private var etc = false


    companion object {
        var reportActivity: ReportActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        boardId = intent.getIntExtra("board_id", 0)
        pinId = intent.getIntExtra("pin_id", 0)
        commentId = intent.getIntExtra("comment_id", 0)

        Log.d("ReportActivity", "Received boardId: $boardId, pinId: $pinId, commentId: $commentId")

        initClickListener()

        reportActivity = this


        binding.reportButton.setOnClickListener {
            sendReport(accessToken, boardId, pinId, commentId)
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

                binding.etcEt.setText("")

                for (view in reasonViews) {
                    view.setTextColor(resources.getColor(R.color.gray40))
                    view.setTypeface(null, Typeface.NORMAL)
                }

                reasonView.setTextColor(resources.getColor(R.color.black))
                reasonView.setTypeface(null, Typeface.BOLD)

                if (reasonView == binding.reportReason8) {
                    binding.etcEt.visibility = View.VISIBLE
                } else {
                    binding.etcEt.visibility = View.GONE
                }
            }
        }

        binding.etcButton.setOnClickListener{
            etc = if (etc) {
                binding.etcEt.visibility = View.GONE
                false
            } else {
                binding.etcEt.visibility = View.VISIBLE
                true
            }
        }

        binding.reportButton.setOnClickListener {
            if (binding.etcEtLayout.visibility == View.VISIBLE) {
                selectedReason = binding.etcEt.text.toString()
                Log.d("ReportActivity", "ETC 사유: $selectedReason")
            }
            sendReport(accessToken, boardId, pinId, commentId)
        }

        binding.reportBackTv.setOnClickListener {
            finish()
        }
    }

    private fun sendReport(accessToken: String, boardId: Int, pinId: Int, commentId: Int) {
        val reason = selectedReason?.takeIf { it.isNotBlank() } + "/" + binding.etcEt.text

        if (reason != null) {
            // 요청 본문 생성
            val reportRequest = when {
                pinId != 0 -> CommentReportRequest(content = reason, pic = arrayListOf())
                commentId != 0 -> CommentReportRequest(content = reason, pic = arrayListOf())
                else -> BoardReportRequest(content = reason)
            }
            Log.d("retrofit_report", reportRequest.toString())

            Log.d("retrofit_report", "이유: $reason")

            when {
                pinId != 0 -> {
                    // 핀 신고 호출
                    val request = reportRequest as CommentReportRequest
                    Log.d("retrofit_report", request.content)
                    CookieClient.service.postPinReport(accessToken, pinId, request)
                        .enqueue(object : Callback<CommentReportResponse> {
                            override fun onResponse(
                                call: Call<CommentReportResponse>,
                                response: Response<CommentReportResponse>
                            ) {
                                handlePinReportResponse(response)
                                Log.d("retrofit_report", response.message())
                                Log.d("retrofit_report", request.content)
                            }

                            override fun onFailure(
                                call: Call<CommentReportResponse>,
                                t: Throwable
                            ) {
                                Log.e("retrofit_report", "네트워크 오류: ${t.message}")
                            }
                        })
                }

                commentId != 0 -> {
                    // 대댓글 신고 호출
                    val request = reportRequest as CommentReportRequest
                    Log.d("retrofit_report", request.content)
                    CookieClient.service.postCommentReport(accessToken, commentId, request)
                        .enqueue(object : Callback<CommentReportResponse> {
                            override fun onResponse(
                                call: Call<CommentReportResponse>,
                                response: Response<CommentReportResponse>
                            ) {
                                handleCommentReportResponse(response)
                                Log.d("retrofit_report", response.message())
                            }

                            override fun onFailure(
                                call: Call<CommentReportResponse>,
                                t: Throwable
                            ) {
                                Log.e("retrofit_report", "네트워크 오류: ${t.message}")
                            }
                        })
                }

                else -> {
                    // 게시물 신고
                    val request = reportRequest as BoardReportRequest
                    Log.d("report", request.content)
                    CookieClient.service.postBoardReport(accessToken, boardId, request)
                        .enqueue(object : Callback<BoardReportResponse> {
                            override fun onResponse(
                                call: Call<BoardReportResponse>,
                                response: Response<BoardReportResponse>
                            ) {
                                handleBoardReportResponse(response)
                                Log.d("report", response.message())
                            }

                            override fun onFailure(call: Call<BoardReportResponse>, t: Throwable) {
                                Log.e("retrofit_report", "네트워크 오류: ${t.message}")
                            }
                        })
                }
            }
        } else {
            Log.e("report", "신고할 이유가 선택되지 않았습니다.")
        }
    }



    private fun handlePinReportResponse(response: Response<CommentReportResponse>) {
        if (response.isSuccessful) {
            val body = response.body()
            if (body?.code == "COMMON200") {
                val intent = Intent(this@ReportActivity, ReportCompleteActivity::class.java)
                startActivity(intent)
                Log.d("report",response.body().toString())
                Log.d("report", "핀 신고가 성공적으로 접수되었습니다.")
            } else {
                Log.e("report", "핀 신고 실패: ${body?.message}")
            }
        } else {
            Log.e("report", "응답 오류: ${response.errorBody()?.string()}")
        }
    }

    private fun handleCommentReportResponse(response: Response<CommentReportResponse>) {
        if (response.isSuccessful) {
            val body = response.body()
            if (body?.code == "COMMON200") {
                val intent = Intent(this@ReportActivity, ReportCompleteActivity::class.java)
                startActivity(intent)
                Log.d("report",response.body().toString())
                Log.d("report", "대댓글 신고가 성공적으로 접수되었습니다.")
            } else {
                Log.e("report", "대댓글 신고 실패: ${body?.message}")
            }
        } else {
            Log.e("report", "응답 오류: ${response.errorBody()?.string()}")
        }
    }

    private fun handleBoardReportResponse(response: Response<BoardReportResponse>) {
        if (response.isSuccessful) {
            val body = response.body()
            if (body?.code == "COMMON200") {
                val intent = Intent(this@ReportActivity, ReportCompleteActivity::class.java)
                startActivity(intent)
                Log.d("report",response.body().toString())
                Log.d("report", "게시물 신고가 성공적으로 접수되었습니다.")
            } else {
                Log.e("report", "게시물 신고 실패: ${body?.message}")
            }
        } else {
            Log.e("report", "응답 오류: ${response.errorBody()?.string()}")
        }
    }
}
