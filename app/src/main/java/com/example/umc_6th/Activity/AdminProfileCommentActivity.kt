package com.example.umc_6th.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.AdminProfileBoardRVAdapter
import com.example.umc_6th.AdminProfileCommentRVAdapter
import com.example.umc_6th.Data.ProfileBoard
import com.example.umc_6th.databinding.ActivityAdminProfileCommentBinding

class AdminProfileCommentActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminProfileCommentBinding

    private val adminprofilecommentList = ArrayList<ProfileBoard>()
    private lateinit var adminprofilecommentAdapter: AdminProfileCommentRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminProfileCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        initRecyclerlist()

        binding.adminProfileCommentBackIv.setOnClickListener {
            finish()
        }
    }

    private fun initRecyclerView() {
        adminprofilecommentAdapter = AdminProfileCommentRVAdapter(adminprofilecommentList)
        adminprofilecommentAdapter.setMyItemClickListener(object : AdminProfileCommentRVAdapter.MyItemClickListener {
            override fun onItemClick(profileBoard: ProfileBoard) {
                val intent = Intent(this@AdminProfileCommentActivity, AdminReportCommentActivity::class.java)
                startActivity(intent)
            }
        })

        binding.adminProfileCommentBodyRv.adapter = adminprofilecommentAdapter
        binding.adminProfileCommentBodyRv.layoutManager = LinearLayoutManager(this)
    }

    private fun initRecyclerlist(){
        adminprofilecommentList.add(ProfileBoard("안녕하셔유 저는 간호학도 감자여유", "qprieiond가 뭔지 모르겠는데, 이 문제 어떻게 푸는건지 알려", "24.07.14"))
        adminprofilecommentList.add(ProfileBoard( "이게 뭔","qprieond가 ", "24.07.14"))
        adminprofilecommentList.add(ProfileBoard( "이게 뭔","qprieond가 ", "24.07.14"))
        adminprofilecommentList.add(ProfileBoard( "이게 뭔","qprieond가 ", "24.07.14"))
        adminprofilecommentList.add(ProfileBoard("안녕하셔유 저는 간호학도 감자여유", "qprieiond가 뭔지 모르겠는데, 이 문제 어떻게 푸는건지 알려", "24.07.14"))
        adminprofilecommentList.add(ProfileBoard("안녕하셔유 저는 간호학도 감자여유", "qprieiond가 뭔지 모르겠는데, 이 문제 어떻게 푸는건지 알려", "24.07.14"))
        adminprofilecommentList.add(ProfileBoard("안녕하셔유 저는 간호학도 감자여유", "qprieiond가 뭔지 모르겠는데, 이 문제 어떻게 푸는건지 알려", "24.07.14"))
    }
}