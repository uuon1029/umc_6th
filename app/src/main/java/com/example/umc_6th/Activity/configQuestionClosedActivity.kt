package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivityConfigQuestionClosedBinding
import com.example.umc_6th.databinding.ActivityConfigReasonClosedBinding
import com.example.umc_6th.databinding.ActivityReportBinding


class configQuestionClosedActivity : AppCompatActivity() {

    lateinit var binding: ActivityConfigQuestionClosedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigQuestionClosedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.configQuestionClosedBackIv.setOnClickListener{
            finish()
        }
    }
}