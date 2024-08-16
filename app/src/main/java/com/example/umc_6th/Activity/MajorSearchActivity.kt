package com.example.umc_6th.Activity

import android.app.Activity
import android.bluetooth.BluetoothClass.Device.Major
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Adapter.MajorboardRecentSearchRVAdapter
import com.example.umc_6th.MainActivity
import com.example.umc_6th.More
import com.example.umc_6th.MoreMajorFragment
import com.example.umc_6th.R
import com.example.umc_6th.RecentSearchRVAdapter
import com.example.umc_6th.Retrofit.BoardSearchMajorResponse
import com.example.umc_6th.Retrofit.DataClass.Board
import com.example.umc_6th.Retrofit.RetrofitClient
import com.example.umc_6th.databinding.ActivityMajorSearchBinding
import retrofit2.Call
import retrofit2.Response


class MajorSearchActivity : AppCompatActivity() {

    lateinit var binding : ActivityMajorSearchBinding
    lateinit var majorRecentSearchRVAdapter: MajorboardRecentSearchRVAdapter
    private var majorRecentSearch = ArrayList<String>()
    val major_id : Int = 1
    var key_word : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMajorSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        majorRecentSearchRVAdapter = MajorboardRecentSearchRVAdapter(majorRecentSearch,
            { position -> removeFromRecentSearch(position) },
            { text -> binding.majorSearchBarEt.setText(text) } // 아이템 클릭 시 EditText에 텍스트 설정
        )

        binding.majorSearchRecentSearchRv.adapter = majorRecentSearchRVAdapter
        binding.majorSearchRecentSearchRv.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        loadRecentSearches()

        binding.majorSearchMainBackIv.setOnClickListener {
            finish()
        }

        binding.majorSearchBtnIv.setOnClickListener {
            val keyWord = binding.majorSearchBarEt.text.toString()
            val searchType = binding.majorSearchTypeTv.text.toString()
            if (keyWord.isNotEmpty()) {
                addToRecentSearch(keyWord)
                binding.majorSearchBarEt.text.clear()
            }

            val spf = getSharedPreferences("searchMajor",Context.MODE_PRIVATE)
            with(spf.edit()) {
                putString("key_wordMajor",keyWord)
                putString("search_typeMajor",searchType)
                apply()
            }
            setResult(Activity.RESULT_OK)
            finish()

        }

        setupDropdown()
    }
    private fun loadRecentSearches() {
        val sharedPreferences = getSharedPreferences("majorrecentSearches", Context.MODE_PRIVATE)
        val savedHistory = sharedPreferences.getStringSet("majorrecentSearch", setOf()) ?: setOf()
        majorRecentSearch.addAll(savedHistory)
        majorRecentSearchRVAdapter.notifyDataSetChanged()
    }
    private fun addToRecentSearch(text:String) {
        val index = majorRecentSearch.indexOf(text)
        if (index != -1) {
            majorRecentSearch.removeAt(index)
            majorRecentSearchRVAdapter.notifyItemRemoved(index)
        }

        majorRecentSearch.add(0, text)
        majorRecentSearchRVAdapter.notifyItemInserted(0)
        saveRecentSearches()
    }

    private fun removeFromRecentSearch(position: Int) {
        majorRecentSearch.removeAt(position)
        majorRecentSearchRVAdapter.notifyItemRemoved(position)
        saveRecentSearches() // 데이터를 저장하는 부분도 업데이트
    }

    private fun saveRecentSearches() {
        val sharedPreferences = getSharedPreferences("majorrecentSearches", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putStringSet("majorrecentSearch",majorRecentSearch.toSet())
            apply()
        }
    }

    private fun setupDropdown() {
        binding.majorSearchTypeOptionBtnCl.setOnClickListener {
            if(binding.majorSearchDropdownCl.visibility == View.GONE) {
                binding.majorSearchDropdownCl.visibility = View.VISIBLE
            } else {
                binding.majorSearchDropdownCl.visibility = View.GONE
            }
        }

        binding.majorSearchDropdownTitleCl.setOnClickListener {
            binding.majorSearchTypeTv.text = "제목"
            binding.majorSearchDropdownCl.visibility = View.GONE
            binding.majorSearchTitleTv.setTypeface(null,android.graphics.Typeface.BOLD)
            binding.majorSearchContentTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.majorSearchBothTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.majorSearchUsernameTv.setTypeface(null,android.graphics.Typeface.NORMAL)
        }

        binding.majorSearchDropdownContentCl.setOnClickListener {
            binding.majorSearchTypeTv.text = "내용"
            binding.majorSearchDropdownCl.visibility = View.GONE
            binding.majorSearchContentTv.setTypeface(null,android.graphics.Typeface.BOLD)
            binding.majorSearchTitleTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.majorSearchBothTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.majorSearchUsernameTv.setTypeface(null,android.graphics.Typeface.NORMAL)
        }

        binding.majorSearchDropdownBothCl.setOnClickListener {
            binding.majorSearchTypeTv.text = "제목+내용"
            binding.majorSearchDropdownCl.visibility = View.GONE
            binding.majorSearchBothTv.setTypeface(null,android.graphics.Typeface.BOLD)
            binding.majorSearchTitleTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.majorSearchContentTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.majorSearchUsernameTv.setTypeface(null,android.graphics.Typeface.NORMAL)
        }

        binding.majorSearchDropdownUsernameCl.setOnClickListener {
            binding.majorSearchTypeTv.text = "글쓴이"
            binding.majorSearchDropdownCl.visibility = View.GONE
            binding.majorSearchUsernameTv.setTypeface(null,android.graphics.Typeface.BOLD)
            binding.majorSearchTitleTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.majorSearchContentTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.majorSearchBothTv.setTypeface(null,android.graphics.Typeface.NORMAL)
        }
    }

}