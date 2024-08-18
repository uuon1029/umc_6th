package com.example.umc_6th.Activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.umc_6th.Adapter.AdminReportBoardRVAdapter
import com.example.umc_6th.Adapter.AdminReportCommentRVAdapter
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.DataClass.Complaint
import com.example.umc_6th.Retrofit.Response.RootBoardComplaintResponse
import com.example.umc_6th.Retrofit.Response.RootComplaintCommentResponse
import com.example.umc_6th.Retrofit.Response.RootComplaintDetailResponse
import com.example.umc_6th.databinding.ActivityAdmin1to1Binding
import com.example.umc_6th.databinding.ActivityAdminReportCommentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AdminReportCommentActivity : AppCompatActivity(){
    lateinit var binding: ActivityAdminReportCommentBinding

    private var adminreportPinList = ArrayList<Complaint>()
    private lateinit var adminreportpinAdapter: AdminReportCommentRVAdapter
    private var pin_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminReportCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pin_id = intent.getIntExtra("boardId", 0)

        getAdminComplaintComment(pin_id)

        binding.adminReportCommentBackIv.setOnClickListener{
            finish()
        }
    }

    private fun getAdminComplaintComment(pinplaintId : Int){
        CookieClient.service.getAdminComplaintPin(pinplaintId).enqueue(object :
            Callback<RootComplaintDetailResponse> {
            override fun onResponse(
                call: Call<RootComplaintDetailResponse>,
                response: Response<RootComplaintDetailResponse>
            ) {
                Glide.with(binding.adminReportCommentProfileIv.context)
                    .load(response.body()?.result?.userPic)
                    .into(binding.adminReportCommentProfileIv)
                binding.adminReportCommentNameTv.text = response.body()?.result?.nickname
                binding.adminReportCommentTimeTv.text = response.body()?.result?.createdAt
                binding.adminReportCommentReportTv.text = response.body()?.result?.report.toString()
                binding.adminReportCommentBodyTv.text = response.body()?.result?.commentContent

                adminreportPinList = response.body()?.result?.complaint!!
                initRecyclerView()
            }

            override fun onFailure(call: Call<RootComplaintDetailResponse>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }
        })
    }
    private fun initRecyclerView() {
        adminreportpinAdapter = AdminReportCommentRVAdapter(adminreportPinList)
        binding.adminReportCommentBodyRv.adapter = adminreportpinAdapter
        binding.adminReportCommentBodyRv.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
    }
}