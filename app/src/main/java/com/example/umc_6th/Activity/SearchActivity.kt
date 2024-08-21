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
import com.example.umc_6th.Adapter.CollegeSelectRVAdapter
import com.example.umc_6th.Adapter.MajorSelectRVAdapter
import com.example.umc_6th.Data.CollegeID
import com.example.umc_6th.Data.MajorID
import com.example.umc_6th.Data.colleges
import com.example.umc_6th.Data.majors
import com.example.umc_6th.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity(){

    lateinit var binding: ActivitySearchBinding
    lateinit var recentSearchRVAdapter: RecentSearchRVAdapter
    private var recentSearch = ArrayList<String>()

    private lateinit var collegeAdapter : CollegeSelectRVAdapter
    private lateinit var majorAdapter : MajorSelectRVAdapter

    companion object {
        var major_id = MainActivity.majorId
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)


        window.statusBarColor = ContextCompat.getColor(this,R.color.white)

        binding.searchMajorTv.text = majors[major_id-1].name

        recentSearchRVAdapter = RecentSearchRVAdapter(recentSearch,
            { position -> removeFromRecentSearch(position) },
            { text -> binding.searchSearchBarEt.setText(text) } // 아이템 클릭 시 EditText에 텍스트 설정
        )

        binding.searchRecentSearchRv.adapter = recentSearchRVAdapter
        binding.searchRecentSearchRv.layoutManager =LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        /*
        supportFragmentManager.beginTransaction()
            .replace(R.id.search_result_main_frm, ExplainFragment())
            .commitAllowingStateLoss()
*/
        loadRecentSearches()

        binding.searchPreviousBtnIv.setOnClickListener {
            finish()

        }

        binding.searchSearchBtnIv.setOnClickListener() {
            val text = binding.searchSearchBarEt.text.toString()
            if (text.isNotEmpty()) {
                addToRecentSearch(text)
                binding.searchSearchBarEt.text.clear()
            }
            val spf = getSharedPreferences("searchText",Context.MODE_PRIVATE)
            with(spf.edit()) {
                putString("input_text",text)
                apply()
            }
            val intent = Intent(this, SearchResultActivity::class.java)
            startActivity(intent)
            finish()
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
                                binding.searchMajorTv.setTextColor(ContextCompat.getColor(this@SearchActivity,R.color.black))
                                major_id = major.id
                            }
                        })
                        binding.searchMajorMajorRv.adapter = majorAdapter
                        binding.searchMajorMajorRv.layoutManager=
                            LinearLayoutManager(this@SearchActivity, LinearLayoutManager.VERTICAL, false)
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
}