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


        //신고하기 버튼 클릭 시 신고 완료 액티비티로 전환
        binding.configReasonClosedNextIb.setOnClickListener{
            val intent: Intent = Intent(this@configReasonClosedActivity, ConfigFragment::class.java)
            startActivity(intent)
        }
    }
}