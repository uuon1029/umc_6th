package com.example.umc_6th.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivityAdmin1to1Binding
import com.example.umc_6th.databinding.ActivityAdminReportCommentBinding


class AdminReportCommentActivity : AppCompatActivity(){
    lateinit var binding: ActivityAdminReportCommentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminReportCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.adminReportCommentBackIv.setOnClickListener{
            finish()
        }
    }
}