package com.example.umc_6th.Activity

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Adapter.AdminQuestRVAdapter
import com.example.umc_6th.Adapter.AdminReportRVAdapter
import com.example.umc_6th.Data.AdminQuest
import com.example.umc_6th.Data.AdminReport
import com.example.umc_6th.databinding.ActivityAdminReportBinding

class AdminReportActivity : AppCompatActivity(){
    lateinit var binding: ActivityAdminReportBinding

    private val adminreportList = ArrayList<AdminReport>()
    private lateinit var adminreportAdapter: AdminReportRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerlist()
        initRecyclerView()

        setSelectedTab(binding.adminReportTabTotalTv)

        binding.adminReportTabTotalTv.setOnClickListener {
            setSelectedTab(binding.adminReportTabTotalTv)
        }

        binding.adminReportTabBoardTv.setOnClickListener {
            setSelectedTab(binding.adminReportTabBoardTv)
        }

        binding.adminReportTabCommentTv.setOnClickListener {
            setSelectedTab(binding.adminReportTabCommentTv)
        }

        binding.adminReportBackIv.setOnClickListener{
            finish()
        }
    }

    private fun setSelectedTab( selectedText: TextView){
        adminReportTabSelections()
        selectedText.isSelected = true
    }
    private fun initRecyclerView() {
        adminreportAdapter = AdminReportRVAdapter(adminreportList)
        binding.adminReportBodyRv.adapter = adminreportAdapter
        binding.adminReportBodyRv.layoutManager = LinearLayoutManager(this)
    }

    private fun adminReportTabSelections(){
        binding.adminReportTabTotalTv.isSelected = false
        binding.adminReportTabBoardTv.isSelected = false
        binding.adminReportTabCommentTv.isSelected = false
    }

    private fun initRecyclerlist(){
        adminreportList.add(AdminReport("커뮤니티","확인 요망", "외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
        adminreportList.add(AdminReport("검색어", "확인 요망","외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
        adminreportList.add(AdminReport("문제", "처리 완료","외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
        adminreportList.add(AdminReport("검색어", "처리 완료","외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
    }
}