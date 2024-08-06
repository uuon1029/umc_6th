package com.example.umc_6th.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivityAdmin1to1Binding
import com.example.umc_6th.databinding.ActivityAdminReportWarningBinding


class AdminReportWaningActivity : AppCompatActivity(){
    lateinit var binding: ActivityAdminReportWarningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminReportWarningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.adminReportWarningBackIv.setOnClickListener{
            finish()
        }
    }
}