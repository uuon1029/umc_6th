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
import com.example.umc_6th.AdminSuspensionReasonActivity
import com.example.umc_6th.AdminWarnReasonActivity
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
    private var userId = 0

    private var board_title = ""
    private var board_content = ""
    private var board_profile = ""
    private var board_nickname = ""

    companion object{
        var adminReportBoardActivity = AdminReportBoardActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminReportBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adminReportBoardActivity = this
        board_id = intent.getIntExtra("boardId", 0)

        getAdminComplaintBoard(board_id)

        binding.adminReportBoardBackIv.setOnClickListener {
            finish()
        }

        binding.adminReportBoardWarningIv.setOnClickListener {
            Log.d("retrofit_warning", listOf(board_id,userId).toString())
            AdminReportWaningActivity.boardId = board_id
            AdminReportWaningActivity.targetUserId = userId
            AdminReportWaningActivity.nickname = board_nickname
            AdminReportWaningActivity.boardTitle = board_title
            AdminReportWaningActivity.boardContent = board_content
            AdminReportWaningActivity.userPic = board_profile
            val intent = Intent(this, AdminWarnReasonActivity::class.java)
            startActivity(intent)
        }

        binding.adminReportBoardSuspensionIv.setOnClickListener {
            AdminReportSuspensionActivity.boardId = board_id
            AdminReportSuspensionActivity.targetUserId = userId
            AdminReportSuspensionActivity.nickname = board_nickname
            AdminReportSuspensionActivity.boardTitle = board_title
            AdminReportSuspensionActivity.boardContent = board_content
            AdminReportSuspensionActivity.userPic = board_profile
            val intent = Intent(this, AdminSuspensionReasonActivity::class.java)
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
                if(response.body()?.result != null){
                    Glide.with(binding.adminReportBoardProfileIv.context)
                        .load(response.body()?.result?.userPic)
                        .into(binding.adminReportBoardProfileIv)
                    binding.adminReportBoardNameTv.text = response.body()?.result?.nickname
                    binding.adminReportBoardTimeTv.text = response.body()?.result?.createdAt
                    binding.adminReportBoardReportTv.text = response.body()?.result?.report.toString()
                    binding.adminReportBoardTitleTv.text = response.body()?.result?.boardTitle
                    binding.adminReportBoardBodyTv.text = response.body()?.result?.boardContent

                    board_title = response.body()?.result?.boardTitle.toString()
                    board_nickname = response.body()?.result?.nickname.toString()
                    board_content = response.body()?.result?.boardContent.toString()
                    board_profile = response.body()?.result?.userPic.toString()

                    userId = response.body()?.result?.userId!!.toInt()

                    adminreportboardList = response.body()?.result?.complaint!!
                    initRecyclerView()
                }
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
