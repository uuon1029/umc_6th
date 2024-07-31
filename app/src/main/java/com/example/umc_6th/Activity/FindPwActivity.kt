package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivityFindPwBinding

class FindPwActivity : AppCompatActivity() {
    lateinit var binding : ActivityFindPwBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindPwBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.findPwBtn.setOnClickListener {
            val i = Intent(this, FindPwPwEditActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}