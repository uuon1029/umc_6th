package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.umc_6th.Activity.AdminProfileBoardActivity
import com.example.umc_6th.Activity.AdminProfileCommentActivity
import com.example.umc_6th.Adapter.AdminUserProfileBoardRVAdapter
import com.example.umc_6th.Adapter.AdminUserProfileCommentRVAdapter
import com.example.umc_6th.Data.AdminUserProfile
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.DataClass.AdminUserProfileBoard
import com.example.umc_6th.Retrofit.DataClass.AdminUserProfileComment
import com.example.umc_6th.Retrofit.Response.RootFindDetailUserResponse
import com.example.umc_6th.databinding.ActivityAdminUserProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminUserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminUserProfileBinding
    private lateinit var postAdapter: AdminUserProfileBoardRVAdapter
    private lateinit var commentAdapter: AdminUserProfileCommentRVAdapter

    private var boardList = ArrayList<AdminUserProfileBoard>()
    private var commentList = ArrayList<AdminUserProfileComment>()

    companion object{
        var profile_user_id = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUserProfile()

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

    private fun getUserProfile() {
        CookieClient.service.getAdminProfile(MainActivity.accessToken, profile_user_id).enqueue(object :
            Callback<RootFindDetailUserResponse>{
            override fun onResponse(
                call: Call<RootFindDetailUserResponse>,
                response: Response<RootFindDetailUserResponse>
            ) {
                val result = response.body()?.result
                if(result != null) {
                    boardList = result.boards
                    commentList = result.pins

                    Glide.with(this@AdminUserProfileActivity).load(result.pic.toString()).into(binding.adminUserProfileImgIv)
                    binding.adminUserProfileNameTv.text = result.nickName

                    binding.adminUserProfileRealNameTv.text = result.name
                    binding.adminUserProfileReportNumTv.text = result.report.toString()
                    binding.adminUserProfileWarningNumTv.text = result.warn.toString()

                    binding.adminUserProfileBg1ReportNumTv.text = result.boardReportCount.toString()
                    binding.adminUserProfileBg2ReportNumTv.text = result.pinsReportCount.toString()
                    initBoardRecyclerView()
                    initCommentRecyclerView()
                }
            }
            override fun onFailure(call: Call<RootFindDetailUserResponse>, t: Throwable) {
                Log.e("retrofit/Root_UserProfile",t.toString())
            }
        })
    }

    private fun initBoardRecyclerView() {
        postAdapter = AdminUserProfileBoardRVAdapter(boardList)
        binding.adminUserProfileBg1Rv.adapter = postAdapter
        binding.adminUserProfileBg1Rv.layoutManager = LinearLayoutManager(this)
    }

    private fun initCommentRecyclerView() {
        commentAdapter = AdminUserProfileCommentRVAdapter(commentList)
        binding.adminUserProfileBg2Rv.adapter = commentAdapter
        binding.adminUserProfileBg2Rv.layoutManager = LinearLayoutManager(this)
    }

}