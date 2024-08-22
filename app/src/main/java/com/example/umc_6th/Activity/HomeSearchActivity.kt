package com.example.umc_6th

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Activity.HomeExampleActivity
import com.example.umc_6th.Adapter.CollegeSelectRVAdapter
import com.example.umc_6th.Adapter.MajorSelectRVAdapter
import com.example.umc_6th.Data.CollegeID
import com.example.umc_6th.Data.MajorID
import com.example.umc_6th.Data.colleges
import com.example.umc_6th.Data.majors
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.Request.majorExampleRequest
import com.example.umc_6th.Retrofit.Response.getExampleResponse
import com.example.umc_6th.databinding.ActivityHomeSearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeSearchActivity : AppCompatActivity(){

    lateinit var binding: ActivityHomeSearchBinding
    lateinit var recentSearchRVAdapter: RecentSearchRVAdapter
    private var recentSearch = ArrayList<String>()

    private lateinit var collegeAdapter : CollegeSelectRVAdapter
    private lateinit var majorAdapter : MajorSelectRVAdapter

    companion object {
        var major_id = MainActivity.majorId
        var text = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this,R.color.white)

        binding.searchMajorTv.text = majors[major_id-1].name

        recentSearchRVAdapter = RecentSearchRVAdapter(recentSearch,
            { position -> removeFromRecentSearch(position) },
            { text -> binding.searchSearchBarEt.setText(text) } // 아이템 클릭 시 EditText에 텍스트 설정
        )

        binding.searchRecentSearchRv.adapter = recentSearchRVAdapter
        binding.searchRecentSearchRv.layoutManager =LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)


        loadRecentSearches()

        binding.searchPreviousBtnIv.setOnClickListener {
            finish()
        }

        binding.searchSearchBtnIv.setOnClickListener() {
            text = binding.searchSearchBarEt.text.toString()
            if (text.isNotEmpty()) {
                addToRecentSearch(text)
                binding.searchSearchBarEt.text.clear()

                getSearchData()
            }
        }

        binding.searchMajorTv.setOnClickListener {
            if(binding.searchMajorRvLayout.visibility == View.GONE) {
                binding.searchMajorRvLayout.visibility = View.VISIBLE
                collegeAdapter = CollegeSelectRVAdapter(colleges)
                collegeAdapter.setClickListener(object : CollegeSelectRVAdapter.MyOnClickeListener{
                    override fun itemClick(college: CollegeID) {
                        binding.searchMajorCollegeTv.text = college.name
                        binding.searchMajorCollegeTv.visibility = View.VISIBLE

                        val majorList = majors.filter { (it.collegeId == college.id) }
                        majorAdapter = MajorSelectRVAdapter(majorList)
                        majorAdapter.setClickListener(object : MajorSelectRVAdapter.MyOnClickeListener{
                            override fun itemClick(major: MajorID) {
                                binding.searchMajorCollegeTv.visibility = View.GONE
                                binding.searchMajorRvLayout.visibility = View.GONE
                                binding.searchMajorTv.text = major.name
                                binding.searchMajorTv.setTextColor(ContextCompat.getColor(this@HomeSearchActivity,R.color.black))
                                major_id = major.id
                            }
                        })
                        binding.searchMajorMajorRv.adapter = majorAdapter
                        binding.searchMajorMajorRv.layoutManager=
                            LinearLayoutManager(this@HomeSearchActivity, LinearLayoutManager.VERTICAL, false)
                    }
                })
                binding.searchMajorMajorRv.adapter = collegeAdapter
                binding.searchMajorMajorRv.layoutManager=
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            } else {
                binding.searchMajorRvLayout.visibility = View.GONE
                binding.searchMajorCollegeTv.visibility = View.GONE
            }
        }
    }

    private fun loadRecentSearches() {
        val sharedPreferences = getSharedPreferences("recentSearches", Context.MODE_PRIVATE)
        val savedHistory = sharedPreferences.getStringSet("recentSearch", setOf()) ?: setOf()
        recentSearch.addAll(savedHistory)
        recentSearchRVAdapter.notifyDataSetChanged()
    }
    private fun addToRecentSearch(text:String) {
        val index = recentSearch.indexOf(text)
        if (index != -1) {
            recentSearch.removeAt(index)
            recentSearchRVAdapter.notifyItemRemoved(index)
        }

        recentSearch.add(0, text)
        recentSearchRVAdapter.notifyItemInserted(0)
        saveRecentSearches()
    }

    private fun removeFromRecentSearch(position: Int) {
        recentSearch.removeAt(position)
        recentSearchRVAdapter.notifyItemRemoved(position)
        saveRecentSearches() // 데이터를 저장하는 부분도 업데이트
    }

    private fun saveRecentSearches() {
        val sharedPreferences = getSharedPreferences("recentSearches", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putStringSet("recentSearch",recentSearch.toSet())
            apply()
        }
    }

    private fun getSearchData(){
        val request = majorExampleRequest(
            majorId = major_id,
            question = text
        )

        CookieClient.service.postMajorFind(MainActivity.accessToken, request).enqueue(object :
            Callback<getExampleResponse>{
            override fun onResponse(
                call: Call<getExampleResponse>,
                response: Response<getExampleResponse>
            ) {
                val result = response.body()?.result
                Log.d("retrofit/example_search",response.toString())

                if(result != null) {
                    HomeExampleActivity.example_tag = result.question
                    HomeExampleActivity.answer_id = result.answerId
                    HomeExampleActivity.question = result.question
                    HomeExampleActivity.content = result.answer
                    HomeExampleActivity.example = result.exampleQuestion
                    HomeExampleActivity.answer = result.correctAnswer

                    val intent = Intent(this@HomeSearchActivity, HomeExampleActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<getExampleResponse>, t: Throwable) {
                Log.e("retrofit/example", t.toString())
            }
            })
    }
}