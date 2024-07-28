package com.example.umc_6th

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity(){

    lateinit var binding: ActivitySearchBinding
    lateinit var recentSearchRVAdapter: RecentSearchRVAdapter
    private var recentSearch = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recentSearchRVAdapter = RecentSearchRVAdapter(recentSearch) { position ->
            removeFromRecentSearch(position)
        }
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

        binding.searchSearchBarEt.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                val text = binding.searchSearchBarEt.text.toString()
                if (text.isNotEmpty()) {
                    addToRecentSearch(text)
                    binding.searchSearchBarEt.text.clear()
                }
                true
            } else {
                false
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