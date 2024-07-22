package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivityConfigReasonClosedBinding
import com.example.umc_6th.databinding.ActivityReportBinding


class configReasonClosedActivity : AppCompatActivity() {

    lateinit var binding: ActivityConfigReasonClosedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigReasonClosedBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.configReasonClosedNextIb.setOnClickListener{
            startActivity(Intent(this, configQuestionClosedActivity::class.java))
        }
        binding.configReasonClosedBackIv.setOnClickListener{
            finish()
        }
    }
}