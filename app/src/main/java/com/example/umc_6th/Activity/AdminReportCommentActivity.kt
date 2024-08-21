package com.example.umc_6th.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.umc_6th.Adapter.AdminReportBoardRVAdapter
import com.example.umc_6th.Adapter.AdminReportCommentRVAdapter
import com.example.umc_6th.AdminSuspensionReasonActivity
import com.example.umc_6th.AdminWarnReasonActivity
import com.example.umc_6th.MainActivity.Companion.userId
import com.example.umc_6th.OtherProfileActivity.Companion.board_id
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
    private var board_id = 0
    private var board_title = ""
    private var board_content = ""
    private var board_profile = ""
    private var board_nickname = ""

    companion object{
        var adminReportCommentActivity = AdminReportCommentActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminReportCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adminReportCommentActivity = this

        pin_id = intent.getIntExtra("boardId", 0)

        getAdminComplaintComment(pin_id)

        binding.adminReportCommentBackIv.setOnClickListener{
            finish()
        }
        binding.adminReportCommentWarningIv.setOnClickListener {
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

        binding.adminReportCommentSuspensionIv.setOnClickListener {
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

                board_id = response.body()?.result!!.boardId
                board_nickname = response.body()?.result?.nickname.toString()
                board_content = response.body()?.result?.commentContent.toString()
                board_profile = response.body()?.result?.userPic.toString()

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