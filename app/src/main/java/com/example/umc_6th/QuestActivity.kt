package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivityMainBinding
import com.example.umc_6th.databinding.ActivityQuestBinding

class QuestActivity : AppCompatActivity() {

    lateinit var binding : ActivityQuestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.questBackIv.setOnClickListener {
            finish()
        }
    }
}