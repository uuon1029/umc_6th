package com.example.umc_6th.Activity

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
        setContentView(binding.root)

        binding.admin1to1SearchSearchBtnIv.setOnClickListener() {
            val text = binding.admin1to1SearchSearchBarEt.text.toString()
            if (text.isNotEmpty()) {
                binding.admin1to1SearchSearchBarEt.text.clear()
            }
            val spf = getSharedPreferences("1to1searchText", Context.MODE_PRIVATE)
            with(spf.edit()) {
                putString("1to1input_text",text)
                apply()
            }
            val intent = Intent(this, Admin1to1Activity::class.java)
            startActivity(intent)
            finish()
        }

        binding.admin1to1SearchPreviousBtnIv.setOnClickListener {
            val i = Intent(this,Admin1to1Activity::class.java)
            startActivity(i)
            finish()
        }
    }
}