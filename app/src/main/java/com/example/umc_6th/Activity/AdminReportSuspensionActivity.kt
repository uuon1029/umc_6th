package com.example.umc_6th.Activity

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivityAdmin1to1Binding
import com.example.umc_6th.databinding.ActivityAdminReportSuspensionBinding


class AdminReportSuspensionActivity : AppCompatActivity(){
    lateinit var binding: ActivityAdminReportSuspensionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminReportSuspensionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSelectedTab(binding.adminReportSuspension3dayTv)
        initDayClickListener()

        binding.adminReportSuspensionBackIv.setOnClickListener{
            finish()
        }
    }
    private fun initDayClickListener() {
        binding.adminReportSuspension3dayTv.setOnClickListener {
            setSelectedTab(binding.adminReportSuspension3dayTv)
        }

        binding.adminReportSuspension7dayTv.setOnClickListener {
            setSelectedTab(binding.adminReportSuspension7dayTv)
        }

        binding.adminReportSuspension14dayTv.setOnClickListener {
            setSelectedTab(binding.adminReportSuspension14dayTv)
        }

        binding.adminReportSuspension30dayTv.setOnClickListener {
            setSelectedTab(binding.adminReportSuspension30dayTv)
        }

        binding.adminReportSuspension90dayTv.setOnClickListener {
            setSelectedTab(binding.adminReportSuspension90dayTv)
        }

        binding.adminReportSuspensionForeverTv.setOnClickListener {
            setSelectedTab(binding.adminReportSuspensionForeverTv)
        }
    }

    private fun setSelectedTab(selectedText: TextView){
        adminQuestTabSelections()
        selectedText.isSelected = true
    }

    private fun adminQuestTabSelections(){
        binding.adminReportSuspension3dayTv.isSelected =false
        binding.adminReportSuspension7dayTv.isSelected = false
        binding.adminReportSuspension14dayTv.isSelected = false
        binding.adminReportSuspension30dayTv.isSelected = false
        binding.adminReportSuspension90dayTv.isSelected = false
        binding.adminReportSuspensionForeverTv.isSelected = false
    }
}