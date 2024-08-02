package com.example.umc_6th.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Adapter.AdminReportBoardRVAdapter
import com.example.umc_6th.AdminProfileBoardRVAdapter
import com.example.umc_6th.Data.AdminReportBoard
import com.example.umc_6th.Data.ProfileBoard
import com.example.umc_6th.More
import com.example.umc_6th.MoreHotBoardRVAdapter
import com.example.umc_6th.QuestActivity
import com.example.umc_6th.R
import com.example.umc_6th.databinding.ActivityAdminProfileBoardBinding

class AdminProfileBoardActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminProfileBoardBinding

    private val adminprofileboardList = ArrayList<ProfileBoard>()
    private lateinit var adminprofileboardAdapter: AdminProfileBoardRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminProfileBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        initRecyclerlist()

        binding.adminProfileBoardBackIv.setOnClickListener {
            finish()
        }
    }
    private fun initRecyclerView() {
        adminprofileboardAdapter = AdminProfileBoardRVAdapter(adminprofileboardList)

        adminprofileboardAdapter.setMyItemClickListener(object : AdminProfileBoardRVAdapter.MyItemClickListener {
            override fun onItemClick(profileBoard: ProfileBoard) {
                val intent = Intent(this@AdminProfileBoardActivity, AdminReportBoardActivity::class.java)
                startActivity(intent)
            }
        })
        binding.adminProfileBoardBodyRv.adapter = adminprofileboardAdapter
        binding.adminProfileBoardBodyRv.layoutManager = LinearLayoutManager(this)
    }

    private fun initRecyclerlist(){
        adminprofileboardList.add(ProfileBoard("안녕하셔유 저는 간호학도 감자여유", "qprieiond가 뭔지 모르겠는데, 이 문제 어떻게 푸는건지 알려", "24.07.14"))
        adminprofileboardList.add(ProfileBoard( "이게 뭔","qprieond가 ", "24.07.14"))
        adminprofileboardList.add(ProfileBoard( "이게 뭔","qprieond가 ", "24.07.14"))
        adminprofileboardList.add(ProfileBoard( "이게 뭔","qprieond가 ", "24.07.14"))
        adminprofileboardList.add(ProfileBoard("안녕하셔유 저는 간호학도 감자여유", "qprieiond가 뭔지 모르겠는데, 이 문제 어떻게 푸는건지 알려", "24.07.14"))
        adminprofileboardList.add(ProfileBoard("안녕하셔유 저는 간호학도 감자여유", "qprieiond가 뭔지 모르겠는데, 이 문제 어떻게 푸는건지 알려", "24.07.14"))
        adminprofileboardList.add(ProfileBoard("안녕하셔유 저는 간호학도 감자여유", "qprieiond가 뭔지 모르겠는데, 이 문제 어떻게 푸는건지 알려", "24.07.14"))
    }
}