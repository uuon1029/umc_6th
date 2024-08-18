package com.example.umc_6th.Activity

import android.app.Activity
import android.content.Context
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
import com.example.umc_6th.Retrofit.RetrofitClient
import com.example.umc_6th.databinding.ActivityAdminQuestBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminQuestActivity : AppCompatActivity(){
    lateinit var binding: ActivityAdminQuestBinding
    private lateinit var adminquestAdapter: AdminQuestRVAdapter
    private var page = 0

    private val SEARCH_REQUEST = 1001

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

        binding.adminQuestSearchIv.setOnClickListener {
            val i = Intent(this,AdminQuestSearchActivity::class.java)
            startActivityForResult(i,SEARCH_REQUEST)
        }

        binding.adminQuestRegisterButton.setOnClickListener {
            val intent = Intent(this, AdminQuestWritingActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SEARCH_REQUEST && resultCode == Activity.RESULT_OK) {
            val spfs = getSharedPreferences("rootText", Context.MODE_PRIVATE)
            val searchWord = spfs.getString("root_text","")
            Log.d("get_word",searchWord.toString())
            if (searchWord != null) {
                initCategorySearchClickListener(searchWord)
            }
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
    private fun initCategorySearchClickListener(searchText: String) {

        if (binding.adminQuestTabTotalTv.isSelected) {
            getSearchAll(searchText, page)
        }
        else if (binding.adminQuestTabSearchTv.isSelected) {
            getSearchWord(searchText, page)
        }
        else if (binding.adminQuestTabCommunityTv.isSelected) {
            getSearchBoard(searchText, page)
        }
        else if (binding.adminQuestTabMatterTv.isSelected) {
            getSearchMajor(searchText, page)
        }

        binding.adminQuestTabTotalTv.setOnClickListener {
            setSelectedTab(binding.adminQuestTabTotalTv)
            getSearchAll(searchText,page)
        }

        binding.adminQuestTabSearchTv.setOnClickListener {
            setSelectedTab(binding.adminQuestTabSearchTv)
            getSearchWord(searchText,page)
        }

        binding.adminQuestTabCommunityTv.setOnClickListener {
            setSelectedTab(binding.adminQuestTabCommunityTv)
            getSearchBoard(searchText,page)
        }

        binding.adminQuestTabMatterTv.setOnClickListener {
            setSelectedTab(binding.adminQuestTabMatterTv)
            getSearchMajor(searchText,page)
        }
    }

    private fun setSelectedTab(selectedTextView: TextView) {
        clearTabSelections()
        selectedTextView.isSelected = true
    }
    private fun initRecyclerView() {
        adminquestAdapter = AdminQuestRVAdapter(this, adminquestList)
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

            override fun onModifyClick(item: Faq) {
                val intent = Intent(this@AdminQuestActivity, AdminQuestWritingActivity::class.java)
                intent.putExtra("title", item.title)
                intent.putExtra("content", item.content)
                intent.putExtra("category", item.category)
                intent.putExtra("faqId", item.faqid)
                startActivity(intent)
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
    private fun getSearchAll(content : String, page : Int) {
        RetrofitClient.service.getFAQSearchAll(MainActivity.accessToken,content, page).enqueue(object :
            Callback<FAQListAllResponse> {
            override fun onFailure(call: Call<FAQListAllResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<FAQListAllResponse>?,
                response: Response<FAQListAllResponse>?
            ) {
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body()?.result != null) {
                            adminquestList = response.body()!!.result.faqList
                        }
                    }
                    initRecyclerView()
                }
                Log.d("retrofit", adminquestList.toString())
            }
        })
    }

    private fun getSearchWord(content : String, page : Int) {
        RetrofitClient.service.getFAQSearchWord(MainActivity.accessToken,content, page).enqueue(object :
            Callback<FAQListAllResponse> {
            override fun onFailure(call: Call<FAQListAllResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<FAQListAllResponse>?,
                response: Response<FAQListAllResponse>?
            ) {
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body()?.result != null) {
                            adminquestList = response.body()!!.result.faqList
                        }
                    }
                    initRecyclerView()
                }
                Log.d("retrofit", adminquestList.toString())
            }
        })
    }
    private fun getSearchBoard(content : String, page : Int) {
        RetrofitClient.service.getFAQSearchBoard(MainActivity.accessToken,content, page).enqueue(object :
            Callback<FAQListAllResponse> {
            override fun onFailure(call: Call<FAQListAllResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<FAQListAllResponse>?,
                response: Response<FAQListAllResponse>?
            ) {
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body()?.result != null) {
                            adminquestList = response.body()!!.result.faqList
                        }
                    }
                    initRecyclerView()
                }
                Log.d("retrofit", adminquestList.toString())
            }
        })
    }
    private fun getSearchMajor(content : String, page : Int) {
        RetrofitClient.service.getFAQSearchMajor(MainActivity.accessToken,content, page).enqueue(object :
            Callback<FAQListAllResponse> {
            override fun onFailure(call: Call<FAQListAllResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<FAQListAllResponse>?,
                response: Response<FAQListAllResponse>?
            ) {
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body()?.result != null) {
                            adminquestList = response.body()!!.result.faqList
                        }
                    }
                    initRecyclerView()
                }
                Log.d("retrofit", adminquestList.toString())
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