package com.example.umc_6th.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.AdminProfileBoardRVAdapter
import com.example.umc_6th.AdminProfileCommentRVAdapter
import com.example.umc_6th.AdminUserProfileActivity
import com.example.umc_6th.Data.ProfileBoard
import com.example.umc_6th.MainActivity
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.DataClass.PinContent
import com.example.umc_6th.Retrofit.Response.RootUserFindReportCommentOrPin
import com.example.umc_6th.databinding.ActivityAdminProfileCommentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminProfileCommentActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminProfileCommentBinding

    private var adminprofilecommentList = ArrayList<PinContent>()
    private lateinit var adminprofilecommentAdapter: AdminProfileCommentRVAdapter

    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminProfileCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUserReportPin()

        binding.adminProfileCommentBackIv.setOnClickListener {
            finish()
        }
    }

    private fun getUserReportPin() {
        CookieClient.service.getAdminProfileComment(MainActivity.accessToken,AdminUserProfileActivity.profile_user_id,page)
            .enqueue(object : Callback<RootUserFindReportCommentOrPin>{
                override fun onResponse(
                    call: Call<RootUserFindReportCommentOrPin>,
                    response: Response<RootUserFindReportCommentOrPin>
                ) {
                    val result = response.body()?.result
                    if(result != null) {
                        adminprofilecommentList = result.content
                        initRecyclerView()
                    }
                }
                override fun onFailure(call: Call<RootUserFindReportCommentOrPin>, t: Throwable) {
                    Log.e("retrofit/Root_UserProfile/Board",t.toString())
                }
            })
    }

    private fun initRecyclerView() {
        adminprofilecommentAdapter = AdminProfileCommentRVAdapter(adminprofilecommentList)
        adminprofilecommentAdapter.setMyItemClickListener(object : AdminProfileCommentRVAdapter.MyItemClickListener {
            override fun onItemClick(item: PinContent) {
                val intent = Intent(this@AdminProfileCommentActivity, AdminReportCommentActivity::class.java)
                intent.putExtra("id",item.complaintId)
                startActivity(intent)
            }
        })
        binding.adminProfileCommentBodyRv.adapter = adminprofilecommentAdapter
        binding.adminProfileCommentBodyRv.layoutManager = LinearLayoutManager(this)
    }
}