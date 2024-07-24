package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivityFindIdResultBinding

class FindIdResultActivity : AppCompatActivity() {
    lateinit var binding: ActivityFindIdResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindIdResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSetOnClickListener()
    }

    private fun initSetOnClickListener() {
        binding.findIdFindPwBtn.setOnClickListener {
            val i = Intent(this, FindPwActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.findIdCompleteBtn.setOnClickListener {
            //로그인 화면으로 이동
        }
    }
}