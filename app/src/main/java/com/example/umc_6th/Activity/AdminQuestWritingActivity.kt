package com.example.umc_6th.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.MainActivity
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.Response.RootQNAViewResponse
import com.example.umc_6th.databinding.ActivityAdminQuestWritingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AdminQuestWritingActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminQuestWritingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminQuestWritingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.adminQuestWritingBackIv.setOnClickListener {
            finish()
        }
    }
}