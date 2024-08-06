package com.example.umc_6th.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivityAdmin1to1AnswerBinding
import com.example.umc_6th.databinding.ActivityAdmin1to1Binding


class Admin1to1AnswerActivity : AppCompatActivity(){
    lateinit var binding: ActivityAdmin1to1AnswerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdmin1to1AnswerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.admin1to1CommentBackIv.setOnClickListener {
            finish()
        }
    }
}