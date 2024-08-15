package com.example.umc_6th.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.umc_6th.R
import com.example.umc_6th.databinding.ActivityAdminAnnouncementBinding
import com.example.umc_6th.databinding.ActivityAdminHomeBinding

class AdminHomeActivity : AppCompatActivity(){
    lateinit var binding: ActivityAdminHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.gray20)

        initOnClickListener()
    }

    private fun initOnClickListener(){
        binding.adminHomeAnnouncementIb.setOnClickListener {
            startActivity(Intent(this, ActivityAdminAnnouncementBinding::class.java))
        }
        binding.adminHomeQuestIb.setOnClickListener {
            startActivity(Intent(this, AdminQuestActivity::class.java))
        }
        binding.adminHome1to1Ib.setOnClickListener {
            startActivity(Intent(this, Admin1to1Activity::class.java))
        }
        binding.adminHomeReportIb.setOnClickListener {
            startActivity(Intent(this, AdminReportActivity::class.java))
        }
        binding.adminHomeMemberManageIb.setOnClickListener {
            startActivity(Intent(this, AdminUserManageActivity::class.java))
        }
    }
}