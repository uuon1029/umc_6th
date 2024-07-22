package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivityFindPwPwEditBinding

class FindPwPwEdit : AppCompatActivity() {
    lateinit var binding : ActivityFindPwPwEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindPwPwEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.findPwPwEditBtn.setOnClickListener {
            // 로그인 연결
        }
    }
}