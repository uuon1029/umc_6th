package com.example.umc_6th.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.R
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.Request.BoardReportRequest
import com.example.umc_6th.databinding.ActivityReportBinding

class ReportActivity : AppCompatActivity() {

    lateinit var binding: ActivityReportBinding
    private var selectedReason: String? = null
    private var boardId: Int = 0
    var accessToken :String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        boardId = intent.getIntExtra("board_id", 0)

        val spf = getSharedPreferences("Auth", MODE_PRIVATE)
        accessToken = spf.getString("accessToken","").toString()

        Log.d("ReportActivity", "Received boardId: $boardId")

        initClickListener()

        var etc = false

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
            sendReport(accessToken,boardId)
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
            binding.reportReason7
        )

        for (reasonView in reasonViews) {
            reasonView.setOnClickListener {
                selectedReason = reasonView.text.toString()
                Log.d("ReportActivity", "Selected reason: $selectedReason")

                for (view in reasonViews) {
                    view.setTextColor(resources.getColor(R.color.gray40))
                }

                reasonView.setTextColor(resources.getColor(R.color.black))
            }
        }

        binding.etcButton.setOnClickListener {
            binding.etcEt.visibility = View.VISIBLE
            selectedReason = binding.etcEt.text.toString()
            Log.d("ReportActivity", "Selected ETC reason: $selectedReason")
        }

        binding.reportButton.setOnClickListener {
            sendReport(accessToken,boardId)
        }
    }

    private fun sendReport(accessToken:String, boardId:Int) {
        if (binding.etcEt.visibility == View.VISIBLE) {
            selectedReason = binding.etcEt.text.toString()
            Log.d("ReportActivity", "Final ETC reason: $selectedReason")
        }

        if (!selectedReason.isNullOrEmpty()) {
            val reportRequest = BoardReportRequest(selectedReason!!)
            Log.d("ReportActivity", "Sending report with reason: $selectedReason")
            CookieClient.service.postBoardReport(accessToken,boardId,reportRequest)
        } else {
            Log.e("ReportActivity", "Report reason is empty. Cannot send report.")
        }
    }
}
