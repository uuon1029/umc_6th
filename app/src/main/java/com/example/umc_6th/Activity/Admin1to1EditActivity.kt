package com.example.umc_6th.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivityAdmin1to1Binding
import com.example.umc_6th.databinding.ActivityAdmin1to1EditBinding


class Admin1to1EditActivity : AppCompatActivity(){
    lateinit var binding: ActivityAdmin1to1EditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdmin1to1EditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.admin1to1CommentBackIv.setOnClickListener {
            finish()
        }
    }
}