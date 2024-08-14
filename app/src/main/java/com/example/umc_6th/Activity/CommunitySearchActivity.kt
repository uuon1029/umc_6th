package com.example.umc_6th.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Adapter.CommunityRecentSearchRVAdapter
import com.example.umc_6th.MainActivity
import com.example.umc_6th.MoreTotalBoardFragment
import com.example.umc_6th.R
import com.example.umc_6th.RecentSearchRVAdapter
import com.example.umc_6th.Retrofit.BoardSearchAllResponse
import com.example.umc_6th.Retrofit.RetrofitClient
import com.example.umc_6th.Retrofit.BoardSearchMajorResponse
import com.example.umc_6th.databinding.ActivityCommunitySearchBinding
import retrofit2.Call
import retrofit2.Response
import java.util.zip.Inflater
import javax.security.auth.callback.Callback

class CommunitySearchActivity : AppCompatActivity() {

    lateinit var binding : ActivityCommunitySearchBinding
    lateinit var commuRecentSearchRVAdapter: CommunityRecentSearchRVAdapter
    private var commuRecentSearch = ArrayList<String>()
    var key_word : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        commuRecentSearchRVAdapter = CommunityRecentSearchRVAdapter(commuRecentSearch,
            { position -> removeFromRecentSearch(position) },
            { text -> binding.commuSearchBarEt.setText(text) } // 아이템 클릭 시 EditText에 텍스트 설정
        )

        binding.commuSearchRecentSearchRv.adapter = commuRecentSearchRVAdapter
        binding.commuSearchRecentSearchRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        loadRecentSearches()

        binding.commuSearchMainBackIv.setOnClickListener {
            finish()
        }

        binding.commuSearchBtnIv.setOnClickListener {
            val keyWord = binding.commuSearchBarEt.text.toString()
            val searchType = binding.commuSearchTypeTv.text.toString()
            if (keyWord.isNotEmpty()) {
                addToRecentSearch(keyWord)
                binding.commuSearchBarEt.text.clear()
            }

            val spf = getSharedPreferences("search",Context.MODE_PRIVATE)
            with(spf.edit()) {
                putString("key_word",keyWord)
                putString("search_type",searchType)
                apply()
            }
            setResult(Activity.RESULT_OK)
            finish()

        }

        setupDropdown()
    }
    private fun loadRecentSearches() {
        val sharedPreferences = getSharedPreferences("commurecentSearches", Context.MODE_PRIVATE)
        val savedHistory = sharedPreferences.getStringSet("commurecentSearch", setOf()) ?: setOf()
        commuRecentSearch.addAll(savedHistory)
        commuRecentSearchRVAdapter.notifyDataSetChanged()
    }
    private fun addToRecentSearch(text:String) {
        val index = commuRecentSearch.indexOf(text)
        if (index != -1) {
            commuRecentSearch.removeAt(index)
            commuRecentSearchRVAdapter.notifyItemRemoved(index)
        }

        commuRecentSearch.add(0, text)
        commuRecentSearchRVAdapter.notifyItemInserted(0)
        saveRecentSearches()
    }

    private fun removeFromRecentSearch(position: Int) {
        commuRecentSearch.removeAt(position)
        commuRecentSearchRVAdapter.notifyItemRemoved(position)
        saveRecentSearches() // 데이터를 저장하는 부분도 업데이트
    }

    private fun saveRecentSearches() {
        val sharedPreferences = getSharedPreferences("commurecentSearches", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putStringSet("commurecentSearch",commuRecentSearch.toSet())
            apply()
        }
    }

    private fun setupDropdown() {
        binding.commuSearchTypeOptionBtnCl.setOnClickListener {
            if(binding.commSearchDropdownCl.visibility == View.GONE) {
                binding.commSearchDropdownCl.visibility = View.VISIBLE
            } else {
                binding.commSearchDropdownCl.visibility = View.GONE
            }
        }

        binding.commuSearchDropdownTitleCl.setOnClickListener {
            binding.commuSearchTypeTv.text = "제목"
            binding.commSearchDropdownCl.visibility = View.GONE
            binding.commuSearchTitleTv.setTypeface(null,android.graphics.Typeface.BOLD)
            binding.commuSearchContentTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.commuSearchBothTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.commuSearchUsernameTv.setTypeface(null,android.graphics.Typeface.NORMAL)
        }

        binding.commuSearchDropdownContentCl.setOnClickListener {
            binding.commuSearchTypeTv.text = "내용"
            binding.commSearchDropdownCl.visibility = View.GONE
            binding.commuSearchContentTv.setTypeface(null,android.graphics.Typeface.BOLD)
            binding.commuSearchTitleTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.commuSearchBothTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.commuSearchUsernameTv.setTypeface(null,android.graphics.Typeface.NORMAL)
        }

        binding.commuSearchDropdownBothCl.setOnClickListener {
            binding.commuSearchTypeTv.text = "제목+내용"
            binding.commSearchDropdownCl.visibility = View.GONE
            binding.commuSearchBothTv.setTypeface(null,android.graphics.Typeface.BOLD)
            binding.commuSearchTitleTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.commuSearchContentTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.commuSearchUsernameTv.setTypeface(null,android.graphics.Typeface.NORMAL)
        }

        binding.commuSearchDropdownUsernameCl.setOnClickListener {
            binding.commuSearchTypeTv.text = "글쓴이"
            binding.commSearchDropdownCl.visibility = View.GONE
            binding.commuSearchUsernameTv.setTypeface(null,android.graphics.Typeface.BOLD)
            binding.commuSearchTitleTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.commuSearchContentTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.commuSearchBothTv.setTypeface(null,android.graphics.Typeface.NORMAL)
        }
    }

}