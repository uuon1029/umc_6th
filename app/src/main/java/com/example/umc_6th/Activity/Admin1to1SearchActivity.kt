package com.example.umc_6th.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ContentView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Adapter.Admin1to1RecentSearchRVAdapter
import com.example.umc_6th.R
import com.example.umc_6th.RecentSearchRVAdapter
import com.example.umc_6th.SearchResultActivity
import com.example.umc_6th.databinding.ActivityAdmin1to1SearchBinding

class Admin1to1SearchActivity : AppCompatActivity() {
    lateinit var binding : ActivityAdmin1to1SearchBinding
    lateinit var recentSearchRVAdapter: Admin1to1RecentSearchRVAdapter
    private var recentSearch = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdmin1to1SearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recentSearchRVAdapter = Admin1to1RecentSearchRVAdapter(recentSearch,
            { position -> removeFromRecentSearch(position) },
            { text -> binding.admin1to1SearchSearchBarEt.setText(text) } // 아이템 클릭 시 EditText에 텍스트 설정
        )

        binding.admin1to1SearchRecentSearchRv.adapter = recentSearchRVAdapter
        binding.admin1to1SearchRecentSearchRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        loadRecentSearches()

        binding.admin1to1SearchSearchBtnIv.setOnClickListener() {
            val text = binding.admin1to1SearchSearchBarEt.text.toString()
            if (text.isNotEmpty()) {
                addToRecentSearch(text)
                val spf = getSharedPreferences("1to1searchText", Context.MODE_PRIVATE)
                with(spf.edit()) {
                    putString("1to1input_text", text)
                    apply()
                }
                binding.admin1to1SearchSearchBarEt.text.clear()
                setResult(Activity.RESULT_OK) // 결과를 설정하고
                finish() // 현재 액티비티를 종료하여 이전 액티비티로 돌아감
            }
        }

        binding.admin1to1SearchPreviousBtnIv.setOnClickListener {
            val i = Intent(this,Admin1to1Activity::class.java)
            startActivity(i)
            finish()
        }
    }
    private fun loadRecentSearches() {
        val sharedPreferences = getSharedPreferences("root1to1recentSearches", Context.MODE_PRIVATE)
        val savedHistory = sharedPreferences.getStringSet("root1to1recentSearch", setOf()) ?: setOf()
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
        val sharedPreferences = getSharedPreferences("root1to1recentSearches", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putStringSet("root1to1recentSearch",recentSearch.toSet())
            apply()
        }
    }
}