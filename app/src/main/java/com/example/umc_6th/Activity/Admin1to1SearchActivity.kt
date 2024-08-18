package com.example.umc_6th.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ContentView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.umc_6th.R
import com.example.umc_6th.SearchResultActivity
import com.example.umc_6th.databinding.ActivityAdmin1to1SearchBinding

class Admin1to1SearchActivity : AppCompatActivity() {
    lateinit var binding : ActivityAdmin1to1SearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdmin1to1SearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.admin1to1SearchSearchBtnIv.setOnClickListener() {
            val text = binding.admin1to1SearchSearchBarEt.text.toString()
            if (text.isNotEmpty()) {
                val spf = getSharedPreferences("1to1searchText", Context.MODE_PRIVATE)
                with(spf.edit()) {
                    putString("1to1input_text", text)
                    apply()
                }
                binding.admin1to1SearchSearchBarEt.text.clear()
                setResult(Activity.RESULT_OK) // 결과를 설정하고
                finish() // 현재 액티비티를 종료하여 이전 액티비티로 돌아감
            }
        }

        binding.admin1to1SearchPreviousBtnIv.setOnClickListener {
            val i = Intent(this,Admin1to1Activity::class.java)
            startActivity(i)
            finish()
        }
    }
}