package com.example.umc_6th.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Adapter.AdminReportRVAdapter
import com.example.umc_6th.Data.AdminReport
import com.example.umc_6th.MainActivity
import com.example.umc_6th.R
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.Response.RootComplaintAllListResponse
import com.example.umc_6th.Retrofit.Response.RootComplaintBoardsResponse
import com.example.umc_6th.Retrofit.Response.RootComplaintCommentResponse
import com.example.umc_6th.databinding.ActivityAdminReportBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminReportActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminReportBinding

    private var page = 1
    private val adminreportList = ArrayList<AdminReport>()
    private lateinit var adminreportAdapter: AdminReportRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        callGetAdminReportList(page)

        setSelectedTab(binding.adminReportTabTotalTv)

        initCategoryClickListener()

        binding.adminReportBackIv.setOnClickListener {
            finish()
        }
    }

    private fun initCategoryClickListener() {
        binding.adminReportTabTotalTv.setOnClickListener {
            setSelectedTab(binding.adminReportTabTotalTv)
            callGetAdminReportList(page)
        }

        binding.adminReportTabBoardTv.setOnClickListener {
            setSelectedTab(binding.adminReportTabBoardTv)
            callGetAdminReportBoardList(page)
        }

        binding.adminReportTabCommentTv.setOnClickListener {
            setSelectedTab(binding.adminReportTabCommentTv)
            callGetAdminReportCommentList(page)
        }
    }

    private fun setSelectedTab(selectedText: TextView) {
        adminReportTabSelections()
        selectedText.isSelected = true
    }

    private fun initRecyclerView() {
        adminreportAdapter = AdminReportRVAdapter(adminreportList)
        binding.adminReportBodyRv.adapter = adminreportAdapter
        binding.adminReportBodyRv.layoutManager = LinearLayoutManager(this)
    }

    private fun adminReportTabSelections() {
        binding.adminReportTabTotalTv.isSelected = false
        binding.adminReportTabBoardTv.isSelected = false
        binding.adminReportTabCommentTv.isSelected = false
    }

    private fun callGetAdminReportList(page: Int) {
        CookieClient.service.getAdminReportList(MainActivity.accessToken, page).enqueue(object :
            Callback<RootComplaintAllListResponse> {
            override fun onFailure(call: Call<RootComplaintAllListResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<RootComplaintAllListResponse>?,
                response: Response<RootComplaintAllListResponse>?
            ) {
                Log.d("retrofit_report", response?.body().toString())
                if (response != null) {
                    if (response.isSuccessful && response.body()?.result!!.adminReportList != null) {
                        adminreportList?.clear()
                        Log.d("retrofit", response.body()!!.result.adminReportList.toString())
                        adminreportList?.addAll(response.body()!!.result.adminReportList)
                        Log.d("retrofit_adminReportList", adminreportList.toString())
                    } else {
                        Log.e("retrofit", "Response failed or body is null")
                    }
                } else {
                    Log.e("retrofit", "Response is null")
                }
                initRecyclerView()
            }
        })
    }

    private fun callGetAdminReportBoardList(page: Int) {
        CookieClient.service.getAdminReportBoardList(MainActivity.accessToken, page).enqueue(object :
            Callback<RootComplaintBoardsResponse> {
            override fun onFailure(call: Call<RootComplaintBoardsResponse>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<RootComplaintBoardsResponse>?,
                response: Response<RootComplaintBoardsResponse>?
            ) {
                if (response != null) {
                    if (response.isSuccessful && response.body()?.result!!.adminReportList != null) {
                        adminreportList?.clear()
                        Log.d("retrofit", response.body()!!.result.adminReportList.toString())
                        adminreportList?.addAll(response.body()!!.result.adminReportList)
                        Log.d("retrofit_adminReportList", adminreportList.toString())
                    } else {
                        Log.e("retrofit", "Response failed or body is null")
                    }
                } else {
                    Log.e("retrofit", "Response is null")
                }
                initRecyclerView()
            }
        })
    }

    private fun callGetAdminReportCommentList(page: Int) {
        CookieClient.service.getAdminReportCommentList(MainActivity.accessToken, page).enqueue(object : Callback<RootComplaintCommentResponse> {
            override fun onFailure(call: Call<RootComplaintCommentResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<RootComplaintCommentResponse>?,
                response: Response<RootComplaintCommentResponse>?
            ) {
                if (response != null) {
                    if (response.isSuccessful && response.body()?.result!!.adminReportList != null) {
                        adminreportList?.clear()
                        Log.d("retrofit", response.body()!!.result.adminReportList.toString())
                        adminreportList?.addAll(response.body()!!.result.adminReportList)
                        Log.d("retrofit_adminReportList", adminreportList.toString())
                    } else {
                        Log.e("retrofit", "Response failed or body is null")
                    }
                } else {
                    Log.e("retrofit", "Response is null")
                }
                initRecyclerView()
            }
        })
    }
}