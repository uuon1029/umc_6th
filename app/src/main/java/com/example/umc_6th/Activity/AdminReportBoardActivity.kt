package com.example.umc_6th.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Adapter.AdminReportBoardRVAdapter
import com.example.umc_6th.Adapter.AdminReportRVAdapter
import com.example.umc_6th.Data.AdminReport
import com.example.umc_6th.Data.AdminReportBoard
import com.example.umc_6th.R
import com.example.umc_6th.databinding.ActivityAdminReportBinding
import com.example.umc_6th.databinding.ActivityAdminReportBoardBinding

class AdminReportBoardActivity : AppCompatActivity(){
    lateinit var binding: ActivityAdminReportBoardBinding

    private val adminreportboardList = ArrayList<AdminReportBoard>()
    private lateinit var adminreportboardAdapter: AdminReportBoardRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminReportBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerlist()
        initRecyclerView()

        binding.adminReportBoardBackIv.setOnClickListener{
            finish()
        }

        findViewById<ImageView>(R.id.admin_report_board_warning_iv).setOnClickListener{
            val intent = Intent(this, AdminReportWaningActivity::class.java)
            startActivity(intent)
        }

        findViewById<ImageView>(R.id.admin_report_board_suspension_iv).setOnClickListener{
            val intent = Intent(this, AdminReportSuspensionActivity::class.java)
            startActivity(intent)
        }
    }
    private fun initRecyclerView() {
        adminreportboardAdapter = AdminReportBoardRVAdapter(adminreportboardList)
        binding.adminReportBoardBodyRv.adapter = adminreportboardAdapter
        binding.adminReportBoardBodyRv.layoutManager = LinearLayoutManager(this)
    }


    private fun initRecyclerlist(){
        adminreportboardList.add(AdminReportBoard(R.drawable.ic_circle_main_40,"레몬 세은", "신고내용신고내용신고내용신고내용신고내용신고내용신고내용신고내용신고내용신고내용신고내용신고내용신고내용신고내용신고내용", "24.07.14"))
        adminreportboardList.add(AdminReportBoard(R.drawable.ic_circle_main_40, "레몬 세은","", "24.07.14"))
        adminreportboardList.add(AdminReportBoard(R.drawable.ic_circle_main_40, "레몬 세은","외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
        adminreportboardList.add(AdminReportBoard(R.drawable.ic_circle_main_40, "레몬 세은","", "24.07.14"))
    }
}