package com.example.umc_6th.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivityAdmin1to1Binding


class Admin1to1Activity : AppCompatActivity(){
    lateinit var binding: ActivityAdmin1to1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdmin1to1Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}