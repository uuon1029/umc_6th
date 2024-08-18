package com.example.umc_6th.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.umc_6th.Adapter.AdminReportBoardRVAdapter
import com.example.umc_6th.Adapter.AdminReportRVAdapter
import com.example.umc_6th.Adapter.AdminUserManageRVAdapter
import com.example.umc_6th.Data.AdminReport
import com.example.umc_6th.Data.AdminReportBoard
import com.example.umc_6th.MainActivity
import com.example.umc_6th.R
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.DataClass.Complaint
import com.example.umc_6th.Retrofit.Response.RootBoardComplaintResponse
import com.example.umc_6th.Retrofit.Response.RootComplaintBoardsResponse
import com.example.umc_6th.Retrofit.Response.RootQNAViewResponse
import com.example.umc_6th.databinding.ActivityAdminReportBinding
import com.example.umc_6th.databinding.ActivityAdminReportBoardBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminReportBoardActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminReportBoardBinding

    private var page = 1
    private var adminreportboardList = ArrayList<Complaint>()
    private lateinit var adminreportboardAdapter: AdminReportBoardRVAdapter
    private var board_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminReportBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        board_id = intent.getIntExtra("boardId", 0)

        getAdminComplaintBoard(board_id)

        binding.adminReportBoardBackIv.setOnClickListener {
            finish()
        }

        findViewById<ImageView>(R.id.admin_report_board_warning_iv).setOnClickListener {
            val intent = Intent(this, AdminReportWaningActivity::class.java)
            startActivity(intent)
        }

        findViewById<ImageView>(R.id.admin_report_board_suspension_iv).setOnClickListener {
            val intent = Intent(this, AdminReportSuspensionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getAdminComplaintBoard(boardId : Int){
        CookieClient.service.getAdminComplaintBoard(boardId).enqueue(object :
            Callback<RootBoardComplaintResponse> {
            override fun onResponse(
                call: Call<RootBoardComplaintResponse>,
                response: Response<RootBoardComplaintResponse>
            ) {
                Glide.with(binding.adminReportBoardProfileIv.context)
                    .load(response.body()?.result?.userPic)
                    .into(binding.adminReportBoardProfileIv)
                binding.adminReportBoardNameTv.text = response.body()?.result?.nickname
                binding.adminReportBoardTimeTv.text = response.body()?.result?.createdAt
                binding.adminReportBoardReportTv.text = response.body()?.result?.report.toString()
                binding.adminReportBoardTitleTv.text = response.body()?.result?.boardTitle
                binding.adminReportBoardBodyTv.text = response.body()?.result?.boardContent

                adminreportboardList = response.body()?.result?.complaint!!
                initRecyclerView()
            }

            override fun onFailure(call: Call<RootBoardComplaintResponse>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }
        })
    }
    private fun initRecyclerView() {
        adminreportboardAdapter = AdminReportBoardRVAdapter(adminreportboardList)
        binding.adminReportBoardBodyRv.adapter = adminreportboardAdapter
        binding.adminReportBoardBodyRv.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }

}
