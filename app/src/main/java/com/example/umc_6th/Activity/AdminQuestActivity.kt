package com.example.umc_6th.Activity

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Adapter.AdminQuestRVAdapter
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.DataClass.Faq
import com.example.umc_6th.Retrofit.Response.FAQListAllResponse
import com.example.umc_6th.databinding.ActivityAdminQuestBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminQuestActivity : AppCompatActivity(){
    lateinit var binding: ActivityAdminQuestBinding
    private lateinit var adminquestAdapter: AdminQuestRVAdapter
    private var page = 1

    private var adminquestList = ArrayList<Faq>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminQuestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initRecyclerlist()
        initRecyclerView()

        setSelectedTab(binding.adminQuestTabTotalTv)

        initCategoryClickListener()

        binding.adminQuestBackIv.setOnClickListener{
            finish()
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
        CookieClient.service.getFAQList(page).enqueue(object :
            Callback<FAQListAllResponse> {
            override fun onFailure(call: Call<FAQListAllResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<FAQListAllResponse>?,
                response: Response<FAQListAllResponse>?
            ) {
//                Log.d("retrofit_test", response?.code().toString())
//                Log.d("retrofit_test", response?.message().toString())
//                Log.d("retrofit_test", response?.body()?.result.toString())
//
//                Log.d("retrofit", response?.body()?.result?.boardMajorList.toString())
//                Log.d("retrofit", response?.body()?.result?.boardHotList.toString())
//                Log.d("retrofit", response?.body()?.result?.boardAllList.toString())

                if (response != null ) {
                    if (response.body() != null){
                        if(response.body()?.result != null) {
                            adminquestList = response.body()!!.result.faqList
                        }
                    }
                    initRecyclerView()
                }
                Log.d("retrofit", adminquestList.toString())
                initRecyclerView()
            }
        })
    }

    private fun getadminFAQSearch(page: Int) {
        CookieClient.service.getFAQSearchList(page).enqueue(object :
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
                        }
                    }
                    initRecyclerView()
                }
                Log.d("retrofit", adminquestList.toString())
                initRecyclerView()
            }
        })
    }

    private fun getadminFAQCommunity(page: Int) {
        CookieClient.service.getFAQCommunityList(page).enqueue(object :
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
                        }
                    }
                    initRecyclerView()
                }
                Log.d("retrofit", adminquestList.toString())
                initRecyclerView()
            }
        })
    }

    private fun getadminFAQExample(page: Int) {
        CookieClient.service.getFAQExampleList(page).enqueue(object :
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
                        }
                    }
                    initRecyclerView()
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