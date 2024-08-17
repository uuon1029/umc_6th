package com.example.umc_6th.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Adapter.AdminReportBoardRVAdapter
import com.example.umc_6th.AdminProfileBoardRVAdapter
import com.example.umc_6th.AdminUserProfileActivity
import com.example.umc_6th.Data.AdminReportBoard
import com.example.umc_6th.Data.ProfileBoard
import com.example.umc_6th.MainActivity
import com.example.umc_6th.More
import com.example.umc_6th.MoreHotBoardRVAdapter
import com.example.umc_6th.QuestActivity
import com.example.umc_6th.R
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.DataClass.Content
import com.example.umc_6th.Retrofit.Response.RootUserFindReportBoards
import com.example.umc_6th.databinding.ActivityAdminProfileBoardBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminProfileBoardActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminProfileBoardBinding

    private var adminprofileboardList = ArrayList<Content>()
    private lateinit var adminprofileboardAdapter: AdminProfileBoardRVAdapter

    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminProfileBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUserReportBoard()

        binding.adminProfileBoardBackIv.setOnClickListener {
            finish()
        }
    }

    private fun getUserReportBoard() {
        CookieClient.service.getAdminProfileBoard(MainActivity.accessToken,AdminUserProfileActivity.profile_user_id, page)
            .enqueue(object : Callback<RootUserFindReportBoards>{
                override fun onResponse(
                    call: Call<RootUserFindReportBoards>,
                    response: Response<RootUserFindReportBoards>
                ) {
                    val result = response.body()?.result

                    if(result != null){
                        adminprofileboardList = result.content
                        initRecyclerView()
                    }
                }

                override fun onFailure(call: Call<RootUserFindReportBoards>, t: Throwable) {
                    Log.e("retrofit/Root_UserProfile/Board",t.toString())
                }
            })
    }


    private fun initRecyclerView() {
        adminprofileboardAdapter = AdminProfileBoardRVAdapter(adminprofileboardList)

        adminprofileboardAdapter.setMyItemClickListener(object : AdminProfileBoardRVAdapter.MyItemClickListener {
            override fun onItemClick(item:Content) {
                val intent = Intent(this@AdminProfileBoardActivity, AdminReportBoardActivity::class.java)
                intent.putExtra("id",item.boardId)
                startActivity(intent)
            }
        })
        binding.adminProfileBoardBodyRv.adapter = adminprofileboardAdapter
        binding.adminProfileBoardBodyRv.layoutManager = LinearLayoutManager(this)
    }
}