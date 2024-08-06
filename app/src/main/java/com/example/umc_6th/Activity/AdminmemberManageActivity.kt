package com.example.umc_6th.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivityAdminMemberManageBinding

class AdminmemberManageActivity : AppCompatActivity(){
    lateinit var binding: ActivityAdminMemberManageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminMemberManageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}