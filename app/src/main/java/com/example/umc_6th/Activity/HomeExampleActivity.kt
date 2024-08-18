package com.example.umc_6th.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivityHomeExampleBinding

class HomeExampleActivity : AppCompatActivity() {
    lateinit var binding : ActivityHomeExampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}