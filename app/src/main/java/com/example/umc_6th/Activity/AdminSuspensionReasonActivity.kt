package com.example.umc_6th

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.Activity.AdminReportSuspensionActivity
import com.example.umc_6th.databinding.ActivityAdminSuspensionReasonBinding

class AdminSuspensionReasonActivity : AppCompatActivity() {

    lateinit var binding: ActivityAdminSuspensionReasonBinding

    private var boardId: Int = 0
    private var pinId: Int = 0
    private var targetuserId: Int = 0
    private var division: String? = null
    private var selectedReason: String? = null
    companion object{
        var adminSuspensionReasonActivity = AdminSuspensionReasonActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminSuspensionReasonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adminSuspensionReasonActivity = this

        boardId = intent.getIntExtra("board_id", 0)
        pinId = intent.getIntExtra("pin_id", 0)

        initClickListener()

        binding.adminSuspensionBackIv.setOnClickListener {
            finish()
        }

        binding.adminSuspensionButton.setOnClickListener {
            if (selectedReason != null) {
                AdminReportSuspensionActivity.reason = selectedReason!!
                val intent = Intent(this, AdminReportSuspensionActivity::class.java)
                intent.putExtra("board_id", boardId)
                intent.putExtra("targetUserId", targetuserId)
                intent.putExtra("division", division)
                intent.putExtra("reason", "신고된 이유 또는 경고 메시지")
                startActivity(intent)
            } else {
                Log.e("retrofit/SuspensionReason", "정지 사유가 선택되지 않았습니다.")
            }
        }
    }

    private fun initClickListener() {
        val reasonViews = listOf<TextView>(
            binding.adminSuspensionReason1,
            binding.adminSuspensionReason2,
            binding.adminSuspensionReason3,
            binding.adminSuspensionReason4,
            binding.adminSuspensionReason5,
            binding.adminSuspensionReason6,
            binding.adminSuspensionReason7,
            binding.adminSuspensionReason8
        )

        for (reasonView in reasonViews) {
            reasonView.setOnClickListener {
                selectedReason = reasonView.text.toString()
                Log.d("retrofit/SuspensionReason", "선택된 이유: $selectedReason")

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
