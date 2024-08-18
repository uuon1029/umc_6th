package com.example.umc_6th

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.Activity.AdminReportWaningActivity
import com.example.umc_6th.databinding.ActivityAdminWarnReasonBinding

class AdminWarnReasonActivity : AppCompatActivity() {

    lateinit var binding: ActivityAdminWarnReasonBinding

    private var selectedReason: String? = null
    private var boardId: Int = 0
    private var pinId: Int = 0
    private var targetuserId: Int = 0
    private var division: String? = null
    var accessToken: String = MainActivity.accessToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminWarnReasonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        boardId = intent.getIntExtra("board_id", 0)
        pinId = intent.getIntExtra("pin_id", 0)

        initClickListener()

        binding.adminWarnBackIv.setOnClickListener {
            finish()
        }

        binding.adminWarnButton.setOnClickListener {
            if (selectedReason != null) {
                val intent = Intent(this, AdminReportWaningActivity::class.java)
                intent.putExtra("board_id", boardId)
                intent.putExtra("targetUserId", targetuserId)
                intent.putExtra("division", division)
                intent.putExtra("reason", "신고된 이유 또는 경고 메시지")
                startActivity(intent)

            } else {
                Log.e("AdminWarnReasonActivity", "경고 사유가 선택되지 않았습니다.")
            }
        }
    }

    private fun initClickListener() {
        val reasonViews = listOf<TextView>(
            binding.adminWarnReason1,
            binding.adminWarnReason2,
            binding.adminWarnReason3,
            binding.adminWarnReason4,
            binding.adminWarnReason5,
            binding.adminWarnReason6,
            binding.adminWarnReason7,
            binding.adminWarnReason8
        )

        for (reasonView in reasonViews) {
            reasonView.setOnClickListener {
                selectedReason = reasonView.text.toString()
                Log.d("AdminWarnReasonActivity", "선택된 이유: $selectedReason")

                for (view in reasonViews) {
                    view.setTextColor(resources.getColor(R.color.gray40))
                    view.setTypeface(null, Typeface.NORMAL)
                }

                reasonView.setTextColor(resources.getColor(R.color.black))
                reasonView.setTypeface(null, Typeface.BOLD)
            }
        }
    }
}
