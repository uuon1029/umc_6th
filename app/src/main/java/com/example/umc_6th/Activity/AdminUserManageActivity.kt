package com.example.umc_6th.Activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.AdminUserManage
import com.example.umc_6th.AdminUserManageRVAdapter
import com.example.umc_6th.R
import com.example.umc_6th.databinding.ActivityAdminUserManageBinding

class AdminUserManageActivity : AppCompatActivity() {

    lateinit var binding : ActivityAdminUserManageBinding
    private val adminusermanageList = ArrayList<AdminUserManage>()
    private lateinit var adminusermanageAdapter: AdminUserManageRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminUserManageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.adminUserManagePreviousBtnIv.setOnClickListener {
            finish()
        }

        initRecyclerView()
        initRecyclerlist()
        setupSearchDropdown()
        setupStateDropdown()

    }
    private fun setupSearchDropdown() {
        binding.adminUserManageSearchTypeOptionBtnCl.setOnClickListener {
            if(binding.adminUserManageSearchDropdownCl.visibility == View.GONE) {
                binding.adminUserManageSearchDropdownCl.visibility = View.VISIBLE
            } else {
                binding.adminUserManageSearchDropdownCl.visibility = View.GONE
            }
        }

        binding.adminUserManageSearchDropdownAllCl.setOnClickListener {
            binding.adminUserManageSearchTypeTv.text = "전체"
            binding.adminUserManageSearchDropdownCl.visibility = View.GONE
            binding.adminUserManageSearchAllTv.setTypeface(null,android.graphics.Typeface.BOLD)
            binding.adminUserManageSearchNicknameTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.adminUserManageSearchNameTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.adminUserManageSearchIdTv.setTypeface(null,android.graphics.Typeface.NORMAL)
        }

        binding.adminUserManageSearchDropdownNicknameCl.setOnClickListener {
            binding.adminUserManageSearchTypeTv.text = "닉네임"
            binding.adminUserManageSearchDropdownCl.visibility = View.GONE
            binding.adminUserManageSearchNicknameTv.setTypeface(null,android.graphics.Typeface.BOLD)
            binding.adminUserManageSearchAllTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.adminUserManageSearchNameTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.adminUserManageSearchIdTv.setTypeface(null,android.graphics.Typeface.NORMAL)
        }

        binding.adminUserManageSearchDropdownNameCl.setOnClickListener {
            binding.adminUserManageSearchTypeTv.text = "실명"
            binding.adminUserManageSearchDropdownCl.visibility = View.GONE
            binding.adminUserManageSearchNameTv.setTypeface(null,android.graphics.Typeface.BOLD)
            binding.adminUserManageSearchAllTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.adminUserManageSearchNicknameTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.adminUserManageSearchIdTv.setTypeface(null,android.graphics.Typeface.NORMAL)
        }

        binding.adminUserManageSearchDropdownIdCl.setOnClickListener {
            binding.adminUserManageSearchTypeTv.text = "아이디"
            binding.adminUserManageSearchDropdownCl.visibility = View.GONE
            binding.adminUserManageSearchIdTv.setTypeface(null,android.graphics.Typeface.BOLD)
            binding.adminUserManageSearchAllTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.adminUserManageSearchNicknameTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.adminUserManageSearchNameTv.setTypeface(null,android.graphics.Typeface.NORMAL)
        }
    }

    private fun setupStateDropdown() {
        binding.adminUserManageStateSelectOptionCl.setOnClickListener {
            binding.adminUserManageStateSelectOptionCl.visibility = View.GONE
            binding.adminUserManageStateDropdownCl.visibility = View.VISIBLE
        }
        binding.adminUserManageStateSelectedCl.setOnClickListener {
            binding.adminUserManageStateSelectOptionCl.visibility = View.VISIBLE
            binding.adminUserManageStateDropdownCl.visibility = View.GONE
        }

        binding.adminUserManageStateDropdownReportCl.setOnClickListener {
            binding.adminUserManageStateDropdownSelectedTv.text = "신고"
            binding.adminUserManageStateSelectOptionSelectedTv.text = "신고"
            binding.adminUserManageStateDropdownReportTv.setTypeface(null,android.graphics.Typeface.BOLD)
            binding.adminUserManageStateDropdownAllTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.adminUserManageStateDropdownSuspensionTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.adminUserManageStateDropdownWarningTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.adminUserManageStateSelectOptionCl.visibility = View.VISIBLE
            binding.adminUserManageStateDropdownCl.visibility = View.GONE
        }

        binding.adminUserManageStateDropdownWarningCl.setOnClickListener {
            binding.adminUserManageStateDropdownSelectedTv.text = "경고"
            binding.adminUserManageStateSelectOptionSelectedTv.text = "경고"
            binding.adminUserManageStateDropdownWarningTv.setTypeface(null,android.graphics.Typeface.BOLD)
            binding.adminUserManageStateDropdownAllTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.adminUserManageStateDropdownSuspensionTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.adminUserManageStateDropdownReportTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.adminUserManageStateSelectOptionCl.visibility = View.VISIBLE
            binding.adminUserManageStateDropdownCl.visibility = View.GONE
        }

        binding.adminUserManageStateDropdownSuspensionCl.setOnClickListener {
            binding.adminUserManageStateDropdownSelectedTv.text = "정지"
            binding.adminUserManageStateSelectOptionSelectedTv.text = "정지"
            binding.adminUserManageStateDropdownSuspensionTv.setTypeface(null,android.graphics.Typeface.BOLD)
            binding.adminUserManageStateDropdownAllTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.adminUserManageStateDropdownReportTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.adminUserManageStateDropdownWarningTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.adminUserManageStateSelectOptionCl.visibility = View.VISIBLE
            binding.adminUserManageStateDropdownCl.visibility = View.GONE
        }

        binding.adminUserManageStateDropdownAllCl.setOnClickListener {
            binding.adminUserManageStateDropdownSelectedTv.text = "전체"
            binding.adminUserManageStateSelectOptionSelectedTv.text = "전체"
            binding.adminUserManageStateDropdownReportTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.adminUserManageStateDropdownAllTv.setTypeface(null,android.graphics.Typeface.BOLD)
            binding.adminUserManageStateDropdownSuspensionTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.adminUserManageStateDropdownWarningTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.adminUserManageStateSelectOptionCl.visibility = View.VISIBLE
            binding.adminUserManageStateDropdownCl.visibility = View.GONE
        }
    }
    private fun initRecyclerView() {
        adminusermanageAdapter = AdminUserManageRVAdapter(adminusermanageList)
        binding.questBoardMainAnswerRv.adapter = adminusermanageAdapter
        binding.questBoardMainAnswerRv.layoutManager = LinearLayoutManager(this)
    }

    private fun initRecyclerlist(){
        adminusermanageList.add(AdminUserManage(R.drawable.ic_circle_main_40,"아이디","닉네임","23.08.04","신고 3"," 경고 1", "정지 4"))
        adminusermanageList.add(AdminUserManage(R.drawable.ic_circle_main_40,"아이디","닉네임","23.08.04","신고 3"," 경고 1", "정지 4"))
        adminusermanageList.add(AdminUserManage(R.drawable.ic_circle_main_40,"아이디","닉네임","23.08.04","신고 3"," 경고 1", "정지 4"))
        adminusermanageList.add(AdminUserManage(R.drawable.ic_circle_main_40,"아이디","닉네임","23.08.04","신고 3"," 경고 1", "정지 4"))
    }

}