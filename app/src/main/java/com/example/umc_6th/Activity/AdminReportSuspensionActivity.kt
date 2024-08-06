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

        setSelectedTab(binding.adminReportSuspension3dayIb, binding.adminReportSuspension3dayTv)

        binding.adminReportSuspension3dayIb.setOnClickListener {
            setSelectedTab(binding.adminReportSuspension3dayIb, binding.adminReportSuspension3dayTv)
        }

        binding.adminReportSuspension7dayIb.setOnClickListener {
            setSelectedTab(binding.adminReportSuspension7dayIb, binding.adminReportSuspension7dayTv)
        }

        binding.adminReportSuspension14dayIb.setOnClickListener {
            setSelectedTab(binding.adminReportSuspension14dayIb, binding.adminReportSuspension14dayTv)
        }

        binding.adminReportSuspension30dayIb.setOnClickListener {
            setSelectedTab(binding.adminReportSuspension30dayIb, binding.adminReportSuspension30dayTv)
        }

        binding.adminReportSuspension90dayIb.setOnClickListener {
            setSelectedTab(binding.adminReportSuspension90dayIb, binding.adminReportSuspension90dayTv)
        }

        binding.adminReportSuspensionForeverIb.setOnClickListener {
            setSelectedTab(binding.adminReportSuspensionForeverIb, binding.adminReportSuspensionForeverTv)
        }


        binding.adminReportSuspensionBackIv.setOnClickListener{
            finish()
        }
    }

    private fun setSelectedTab(selectedButton: ImageButton, selectedText: TextView){
        adminQuestTabSelections()

        selectedButton.isSelected = true
        selectedText.isSelected = true
    }

    private fun adminQuestTabSelections(){
        binding.adminReportSuspension3dayIb.isSelected = false
        binding.adminReportSuspension3dayTv.isSelected =false
        binding.adminReportSuspension7dayIb.isSelected = false
        binding.adminReportSuspension7dayTv.isSelected = false
        binding.adminReportSuspension14dayIb.isSelected = false
        binding.adminReportSuspension14dayTv.isSelected = false
        binding.adminReportSuspension30dayIb.isSelected = false
        binding.adminReportSuspension30dayTv.isSelected = false
        binding.adminReportSuspension90dayIb.isSelected = false
        binding.adminReportSuspension90dayTv.isSelected = false
        binding.adminReportSuspensionForeverIb.isSelected = false
        binding.adminReportSuspensionForeverTv.isSelected = false
    }
}