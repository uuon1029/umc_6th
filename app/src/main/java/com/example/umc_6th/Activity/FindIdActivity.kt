package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivityFindIdBinding

class FindIdActivity : AppCompatActivity(){
    lateinit var binding : ActivityFindIdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindIdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.findIdBtn.setOnClickListener {
            val i = Intent(this, FindIdResultActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}