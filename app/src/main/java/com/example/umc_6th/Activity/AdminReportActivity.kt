package com.example.umc_6th.Activity

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Adapter.AdminReportRVAdapter
import com.example.umc_6th.Data.AdminReport
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.Response.RootComplaintAllListResponse
import com.example.umc_6th.databinding.ActivityAdminReportBinding
import org.checkerframework.checker.units.qual.t
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminReportActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminReportBinding

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

        callGetAdminReportList()
    }

    private fun callGetAdminReportList() {
        CookieClient.service.getAdminReportList(1).enqueue(object : Callback<RootComplaintAllListResponse> {
            override fun onFailure(call: Call<RootComplaintAllListResponse>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(call: Call<RootComplaintAllListResponse>, response: Response<RootComplaintAllListResponse>) {
                if (response.isSuccessful && response.body()?.result != null) {
                    adminreportList.clear()
                    adminreportList.addAll(response.body()!!.result.adminReportList)
                    adminreportAdapter.notifyDataSetChanged()
                }
                Log.d("retrofittttt", response.toString())
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