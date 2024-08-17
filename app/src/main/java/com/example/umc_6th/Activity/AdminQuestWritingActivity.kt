package com.example.umc_6th.Activity

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
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

        //스피너에 들어갈 카테고리
        val items = listOf("검색어", "커뮤니티", "문제")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, items)
        //드롭다운 리소스 지정 (기본)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        //스피너에 어댑터 연결
        binding.adminQuestWritingCatregory.adapter = adapter

        //binding.


    }
}