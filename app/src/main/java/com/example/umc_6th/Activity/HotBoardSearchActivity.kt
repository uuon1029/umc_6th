package com.example.umc_6th.Activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Adapter.HotboardRecentSearchRVAdapter
import com.example.umc_6th.R
import com.example.umc_6th.RecentSearchRVAdapter
import com.example.umc_6th.Retrofit.BoardSearchAllResponse
import com.example.umc_6th.Retrofit.BoardSearchHotResponse
import com.example.umc_6th.Retrofit.RetrofitClient
import com.example.umc_6th.databinding.ActivityHotboardSearchBinding
import com.example.umc_6th.databinding.ActivityMajorSearchBinding
import retrofit2.Call
import retrofit2.Response

class HotBoardSearchActivity : AppCompatActivity() {
    lateinit var binding : ActivityHotboardSearchBinding
    lateinit var hotboardRecentSearchRVAdapter : HotboardRecentSearchRVAdapter
    private var hotboardrecentSearch = ArrayList<String>()
    var key_word : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotboardSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hotboardRecentSearchRVAdapter = HotboardRecentSearchRVAdapter(hotboardrecentSearch,
            { position -> removeFromRecentSearch(position) },
            { text -> binding.hotboardSearchBarEt.setText(text) } // 아이템 클릭 시 EditText에 텍스트 설정
        )

        binding.hotboardSearchRecentSearchRv.adapter = hotboardRecentSearchRVAdapter
        binding.hotboardSearchRecentSearchRv.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        loadRecentSearches()

        binding.hotboardSearchMainBackIv.setOnClickListener {
            finish()
        }

        binding.hotboardSearchBtnIv.setOnClickListener {
            val keyWord = binding.hotboardSearchBarEt.text.toString()
            val searchType = binding.hotboardSearchTypeTv.text.toString()

            if (keyWord.isNotEmpty()) {
                addToRecentSearch(keyWord)
                binding.hotboardSearchBarEt.text.clear()
            }

            val spf = getSharedPreferences("searchHot", Context.MODE_PRIVATE)
            with(spf.edit()) {
                putString("key_wordHot",keyWord)
                putString("search_typeHot",searchType)
                apply()
            }
            setResult(Activity.RESULT_OK)
            finish()
        }

        setupDropdown()
    }
    private fun loadRecentSearches() {
        val sharedPreferences = getSharedPreferences("hotrecentSearches", Context.MODE_PRIVATE)
        val savedHistory = sharedPreferences.getStringSet("hotrecentSearch", setOf()) ?: setOf()
        hotboardrecentSearch.addAll(savedHistory)
        hotboardRecentSearchRVAdapter.notifyDataSetChanged()
    }
    private fun addToRecentSearch(text:String) {
        val index = hotboardrecentSearch.indexOf(text)
        if (index != -1) {
            hotboardrecentSearch.removeAt(index)
            hotboardRecentSearchRVAdapter.notifyItemRemoved(index)
        }

        hotboardrecentSearch.add(0, text)
        hotboardRecentSearchRVAdapter.notifyItemInserted(0)
        saveRecentSearches()
    }

    private fun removeFromRecentSearch(position: Int) {
        hotboardrecentSearch.removeAt(position)
        hotboardRecentSearchRVAdapter.notifyItemRemoved(position)
        saveRecentSearches() // 데이터를 저장하는 부분도 업데이트
    }

    private fun saveRecentSearches() {
        val sharedPreferences = getSharedPreferences("hotrecentSearches", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putStringSet("hotrecentSearch",hotboardrecentSearch.toSet())
            apply()
        }
    }

    private fun setupDropdown() {
        binding.hotboardSearchTypeOptionBtnCl.setOnClickListener {
            if(binding.hotboardSearchDropdownCl.visibility == View.GONE) {
                binding.hotboardSearchDropdownCl.visibility = View.VISIBLE
            } else {
                binding.hotboardSearchDropdownCl.visibility = View.GONE
            }
        }

        binding.hotboardSearchDropdownTitleCl.setOnClickListener {
            binding.hotboardSearchTypeTv.text = "제목"
            binding.hotboardSearchDropdownCl.visibility = View.GONE
            binding.hotboardSearchTitleTv.setTypeface(null,android.graphics.Typeface.BOLD)
            binding.hotboardSearchContentTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.hotboardSearchBothTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.hotboardSearchUsernameTv.setTypeface(null,android.graphics.Typeface.NORMAL)
        }

        binding.hotboardSearchDropdownContentCl.setOnClickListener {
            binding.hotboardSearchTypeTv.text = "내용"
            binding.hotboardSearchDropdownCl.visibility = View.GONE
            binding.hotboardSearchContentTv.setTypeface(null,android.graphics.Typeface.BOLD)
            binding.hotboardSearchTitleTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.hotboardSearchBothTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.hotboardSearchUsernameTv.setTypeface(null,android.graphics.Typeface.NORMAL)
        }

        binding.hotboardSearchDropdownBothCl.setOnClickListener {
            binding.hotboardSearchTypeTv.text = "제목+내용"
            binding.hotboardSearchDropdownCl.visibility = View.GONE
            binding.hotboardSearchBothTv.setTypeface(null,android.graphics.Typeface.BOLD)
            binding.hotboardSearchTitleTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.hotboardSearchContentTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.hotboardSearchUsernameTv.setTypeface(null,android.graphics.Typeface.NORMAL)
        }

        binding.hotboardSearchDropdownUsernameCl.setOnClickListener {
            binding.hotboardSearchTypeTv.text = "글쓴이"
            binding.hotboardSearchDropdownCl.visibility = View.GONE
            binding.hotboardSearchUsernameTv.setTypeface(null,android.graphics.Typeface.BOLD)
            binding.hotboardSearchTitleTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.hotboardSearchContentTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.hotboardSearchBothTv.setTypeface(null,android.graphics.Typeface.NORMAL)
        }
    }
}