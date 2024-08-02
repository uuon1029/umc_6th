package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Activity.AdminProfileBoardActivity
import com.example.umc_6th.Activity.AdminProfileCommentActivity
import com.example.umc_6th.Data.AdminUserProfile
import com.example.umc_6th.databinding.ActivityAdminUserProfileBinding

class AdminUserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminUserProfileBinding
    private lateinit var postAdapter: AdminUserProfileRVAdapter
    private lateinit var commentAdapter: AdminUserProfileRVAdapter

    private val postList = ArrayList<AdminUserProfile>()
    private val commentList = ArrayList<AdminUserProfile>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPostRecyclerView()
        initCommentRecyclerView()
        initializeUserProfileDatas()

        binding.adminUserProfileBackIv.setOnClickListener{
            finish()
        }

        binding.adminUserProfileBg1Iv.setOnClickListener{
            val intent = Intent(this@AdminUserProfileActivity, AdminProfileBoardActivity::class.java)
            startActivity(intent)
        }
        binding.adminUserProfileBg2Iv.setOnClickListener{
            val intent = Intent(this@AdminUserProfileActivity, AdminProfileCommentActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initPostRecyclerView() {
        postAdapter = AdminUserProfileRVAdapter(postList)
        binding.adminUserProfileBg1Rv.adapter = postAdapter
        binding.adminUserProfileBg1Rv.layoutManager = LinearLayoutManager(this)
    }

    private fun initCommentRecyclerView() {
        commentAdapter = AdminUserProfileRVAdapter(commentList)
        binding.adminUserProfileBg2Rv.adapter = commentAdapter
        binding.adminUserProfileBg2Rv.layoutManager = LinearLayoutManager(this)
    }

    private fun initializeUserProfileDatas() {
        // 샘플 데이터 로드 (임시 데이터)
        postList.add(AdminUserProfile("안녕하셔유 저는 간호학도 감자여유"))
        postList.add(AdminUserProfile("안녕하셔유 저는 간호학도 감자여유"))
        postList.add(AdminUserProfile("안녕하셔유 저는 간호학도 감자여유"))

        commentList.add(AdminUserProfile("안녕하셔유 저는 간호학도 감자여유"))
        commentList.add(AdminUserProfile("안녕하셔유 저는 간호학도 감자여유"))
        commentList.add(AdminUserProfile("안녕하셔유 저는 간호학도 감자여유"))

        postAdapter.notifyDataSetChanged()
        commentAdapter.notifyDataSetChanged()
    }
}