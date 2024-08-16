package com.example.umc_6th.Activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivityUserCancellation2Binding

class UserCancellation2Activity : AppCompatActivity() {
    lateinit var binding: ActivityUserCancellation2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserCancellation2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.userCancellationBtn.setOnClickListener {

        }
    }

}