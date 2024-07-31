package com.example.umc_6th.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivityAdminQuestBinding

class AdminQuestActivity : AppCompatActivity(){
    lateinit var binding: ActivityAdminQuestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminQuestBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}