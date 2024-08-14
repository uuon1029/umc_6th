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

        val user_id = intent.getStringExtra("id")
        var date = intent.getStringExtra("date").toString()
        date = date.substring(0,4) + "." + date.substring(5,7) + "." + date.substring(8,10)

        binding.findIdIdTv.text = user_id
        binding.findIdDateTv.text = date + "에 가입된 계정입니다."

        initSetOnClickListener()
    }

    private fun initSetOnClickListener() {
        binding.findIdFindPwBtn.setOnClickListener {
            val i = Intent(this, FindPwActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.findIdCompleteBtn.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}