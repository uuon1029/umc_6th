package com.example.umc_6th.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Adapter.AdminReportRVAdapter
import com.example.umc_6th.Adapter.AdminUserManageRVAdapter
import com.example.umc_6th.Data.AdminReport
import com.example.umc_6th.R
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.DataClass.User
import com.example.umc_6th.Retrofit.Response.RootFindUsersResponse
import com.example.umc_6th.databinding.ActivityAdminUserManageBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminUserManageActivity : AppCompatActivity() {

    lateinit var binding : ActivityAdminUserManageBinding

    private var usersList = ArrayList<User>()
    lateinit var userManageRVAdapter: AdminUserManageRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminUserManageBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.adminUserManagePreviousBtnIv.setOnClickListener {
            finish()
        }

        binding.adminUserManageSearchBtnIv.setOnClickListener {
            val searchType = binding.adminUserManageSearchTypeTv.text.toString()
            val keyWord = binding.adminUserManageSearchBarEt.text.toString()
            searchUsers(searchType,keyWord)
        }

        setupSearchDropdown()

    }
    private fun initRecyclerView() {
        userManageRVAdapter = AdminUserManageRVAdapter(usersList)
        binding.adminUserManageUsersRv.adapter = userManageRVAdapter
        binding.adminUserManageUsersRv.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }
    private fun searchUsers(searchType : String,keyWord: String) {
        when (searchType) {
            "전체" -> searchAll(keyWord)
            "닉네임" -> searchNickname(keyWord)
            "실명" -> searchName(keyWord)
            "아이디" -> searchAccount(keyWord)
        }
    }
    private fun searchAll(keyword:String, paging:Int=1) {
        CookieClient.service.getRootFindUsersAll(keyword, paging).enqueue(object :
            Callback<RootFindUsersResponse> {
            override fun onResponse(
                call: Call<RootFindUsersResponse>,
                response: Response<RootFindUsersResponse>
            ) {
                usersList = response.body()?.result?.content!!
                initRecyclerView()
                Log.d("retrofit_result",response.body()?.result?.content.toString())
            }

            override fun onFailure(call: Call<RootFindUsersResponse>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }

        })
    }
    private fun searchNickname(keyword:String, paging:Int=1) {
        CookieClient.service.getRootFindUsersNickname(keyword, paging).enqueue(object :
            Callback<RootFindUsersResponse> {
            override fun onResponse(
                call: Call<RootFindUsersResponse>,
                response: Response<RootFindUsersResponse>
            ) {
                usersList = response.body()?.result?.content!!
                initRecyclerView()
                Log.d("retrofit_result",response.body()?.result?.content.toString())
            }

            override fun onFailure(call: Call<RootFindUsersResponse>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }

        })
    }
    private fun searchName(keyword:String, paging:Int=1) {
        CookieClient.service.getRootFindUsersName(keyword, paging).enqueue(object :
            Callback<RootFindUsersResponse> {
            override fun onResponse(
                call: Call<RootFindUsersResponse>,
                response: Response<RootFindUsersResponse>
            ) {
                usersList = response.body()?.result?.content!!
                initRecyclerView()
                Log.d("retrofit_result",response.body()?.result?.content.toString())
            }

            override fun onFailure(call: Call<RootFindUsersResponse>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }

        })
    }
    private fun searchAccount(keyword:String, paging:Int=1) {
        CookieClient.service.getRootFindUsersAccount(keyword, paging).enqueue(object :
            Callback<RootFindUsersResponse> {
            override fun onResponse(
                call: Call<RootFindUsersResponse>,
                response: Response<RootFindUsersResponse>
            ) {
                usersList = response.body()?.result?.content!!
                initRecyclerView()
                Log.d("retrofit_result",response.body()?.result?.content.toString())
            }

            override fun onFailure(call: Call<RootFindUsersResponse>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }

        })
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
}