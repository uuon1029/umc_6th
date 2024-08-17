package com.example.umc_6th.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Adapter.AdminQuestRVAdapter
import com.example.umc_6th.MainActivity
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.DataClass.Faq
import com.example.umc_6th.Retrofit.Response.FAQListAllResponse
import com.example.umc_6th.Retrofit.Response.RootFAQDeleteResponse
import com.example.umc_6th.databinding.ActivityAdminQuestBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminQuestActivity : AppCompatActivity(){
    lateinit var binding: ActivityAdminQuestBinding
    private lateinit var adminquestAdapter: AdminQuestRVAdapter
    private var page = 0

    private var adminquestList = ArrayList<Faq>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminQuestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getadminFAQAll(page)

        setSelectedTab(binding.adminQuestTabTotalTv)

        initCategoryClickListener()

        binding.adminQuestBackIv.setOnClickListener{
            finish()
        }

        binding.adminQuestRegisterButton.setOnClickListener {
            val intent = Intent(this, AdminQuestWritingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initCategoryClickListener() {
        binding.adminQuestTabTotalTv.setOnClickListener {
            setSelectedTab(binding.adminQuestTabTotalTv)
            getadminFAQAll(page)
        }

        binding.adminQuestTabSearchTv.setOnClickListener {
            setSelectedTab(binding.adminQuestTabSearchTv)
            getadminFAQSearch(page)
        }

        binding.adminQuestTabCommunityTv.setOnClickListener {
            setSelectedTab(binding.adminQuestTabCommunityTv)
            getadminFAQCommunity(page)
        }

        binding.adminQuestTabMatterTv.setOnClickListener {
            setSelectedTab(binding.adminQuestTabMatterTv)
            getadminFAQExample(page)
        }
    }

    private fun setSelectedTab(selectedTextView: TextView) {
        clearTabSelections()
        selectedTextView.isSelected = true
    }
    private fun initRecyclerView() {
        adminquestAdapter = AdminQuestRVAdapter(adminquestList)
        adminquestAdapter.setMyItemClickListener(object : AdminQuestRVAdapter.MyItemClickListener{

            override fun onDeleteClick(item: Faq) {
                CookieClient.service.deleteFAQ(MainActivity.accessToken, item.faqid).enqueue(object : Callback<RootFAQDeleteResponse>{
                    override fun onResponse(call: Call<RootFAQDeleteResponse>, response: Response<RootFAQDeleteResponse>) {
                        if (response.isSuccessful && response.body()?.isSuccess == true) {
                            adminquestAdapter.removeItem(item)
                            Log.d("adminquestdelete", response.toString())
                        } else {
                            Log.e("AdminQuestRVAdapter", "FAQ 삭제 실패: ${response}")
                        }
                    }

                    override fun onFailure(call: Call<RootFAQDeleteResponse>, t: Throwable) {
                        Log.e("DeleteError", "Failed to delete comment", t)
                    }
                })
            }
        })
        binding.adminQuestBodyRv.adapter = adminquestAdapter
        binding.adminQuestBodyRv.layoutManager = LinearLayoutManager(this)
    }

    private fun clearTabSelections() {
        binding.adminQuestTabTotalTv.isSelected = false
        binding.adminQuestTabSearchTv.isSelected = false
        binding.adminQuestTabCommunityTv.isSelected = false
        binding.adminQuestTabMatterTv.isSelected = false
    }

    private fun getadminFAQAll(page: Int) {
        CookieClient.service.getFAQList(MainActivity.accessToken,page).enqueue(object :
            Callback<FAQListAllResponse> {
            override fun onFailure(call: Call<FAQListAllResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<FAQListAllResponse>?,
                response: Response<FAQListAllResponse>?
            ) {
                Log.d("retrofit_faq", response?.body().toString())
                if (response != null ) {
                    if (response.body() != null){
                        if(response.body()?.result != null) {
                            adminquestList = response.body()!!.result.faqList
                            Log.d("retrofit_faqList", adminquestList.toString())
                        }
                    }
                }
                Log.d("retrofit", adminquestList.toString())
                initRecyclerView()
            }
        })
    }

    private fun getadminFAQSearch(page: Int) {
        CookieClient.service.getFAQSearchList(MainActivity.accessToken,page).enqueue(object :
            Callback<FAQListAllResponse> {
            override fun onFailure(call: Call<FAQListAllResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<FAQListAllResponse>?,
                response: Response<FAQListAllResponse>?
            ) {
                if (response != null ) {
                    if (response.body() != null){
                        if(response.body()?.result != null) {
                            adminquestList = response.body()!!.result.faqList
                            Log.d("retrofit_faqList", adminquestList.toString())
                        }
                    }
                }
                Log.d("retrofit", adminquestList.toString())
                initRecyclerView()
            }
        })
    }

    private fun getadminFAQCommunity(page: Int) {
        CookieClient.service.getFAQCommunityList(MainActivity.accessToken,page).enqueue(object :
            Callback<FAQListAllResponse> {
            override fun onFailure(call: Call<FAQListAllResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<FAQListAllResponse>?,
                response: Response<FAQListAllResponse>?
            ) {
                if (response != null ) {
                    if (response.body() != null){
                        if(response.body()?.result != null) {
                            adminquestList = response.body()!!.result.faqList
                            Log.d("retrofit_faqList", adminquestList.toString())
                        }
                    }
                }
                Log.d("retrofit", adminquestList.toString())
                initRecyclerView()
            }
        })
    }

    private fun getadminFAQExample(page: Int) {
        CookieClient.service.getFAQExampleList(MainActivity.accessToken,page).enqueue(object :
            Callback<FAQListAllResponse> {
            override fun onFailure(call: Call<FAQListAllResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<FAQListAllResponse>?,
                response: Response<FAQListAllResponse>?
            ) {
                if (response != null ) {
                    if (response.body() != null){
                        if(response.body()?.result != null) {
                            adminquestList = response.body()!!.result.faqList
                            Log.d("retrofit_faqList", adminquestList.toString())
                        }
                    }
                }
                Log.d("retrofit", adminquestList.toString())
                initRecyclerView()
            }
        })
    }

//    private fun initRecyclerlist(){
//        adminquestList.add(AdminQuest("커뮤니티", "외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
//        adminquestList.add(AdminQuest("검색어", "외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
//        adminquestList.add(AdminQuest("문제", "외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
//        adminquestList.add(AdminQuest("검색어", "외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
//    }
}