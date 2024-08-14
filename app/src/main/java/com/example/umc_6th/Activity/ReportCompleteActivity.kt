package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.Activity.ReportActivity
import com.example.umc_6th.databinding.ActivityReportCompleteBinding

class ReportCompleteActivity : AppCompatActivity() {

    lateinit var binding: ActivityReportCompleteBinding
    val reportActivity = ReportActivity.reportActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.reportCompleteBackButton.setOnClickListener {
            finish()
        }

        binding.reportButton.setOnClickListener{
            reportActivity?.finish()
            finish()
        }
    }
}