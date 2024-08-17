package com.example.umc_6th.Activity

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Adapter.AdminReportRVAdapter
import com.example.umc_6th.Data.AdminReport
import com.example.umc_6th.MainActivity
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.Response.RootComplaintAllListResponse
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

        binding.adminReportBackIv.setOnClickListener {
            finish()
        }

        callGetAdminReportList(page)
    }

    private fun callGetAdminReportList(page: Int) {
        CookieClient.service.getAdminReportList(MainActivity.accessToken, page).enqueue(object : Callback<RootComplaintAllListResponse> {
            override fun onFailure(call: Call<RootComplaintAllListResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(call: Call<RootComplaintAllListResponse>?, response: Response<RootComplaintAllListResponse>?) {
                if (response != null) {
                    Log.d("retrofit", response.body().toString())
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
}