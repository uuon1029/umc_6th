package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivityFindPwPwEditBinding

class FindPwPwEditActivity : AppCompatActivity() {
    lateinit var binding : ActivityFindPwPwEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindPwPwEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.findPwPwEditBtn.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}