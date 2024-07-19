package com.example.umc_6th

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivityReportCompleteBinding

class ReportCompleteActivity : AppCompatActivity() {

    lateinit var binding: ActivityReportCompleteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}