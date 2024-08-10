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
import com.example.umc_6th.R
import com.example.umc_6th.Retrofit.BoardSearchAllResponse
import com.example.umc_6th.Retrofit.BoardSearchHotResponse
import com.example.umc_6th.Retrofit.RetrofitClient
import com.example.umc_6th.databinding.ActivityHotboardSearchBinding
import com.example.umc_6th.databinding.ActivityMajorSearchBinding
import retrofit2.Call
import retrofit2.Response

class HotBoardSearchActivity : AppCompatActivity() {
    lateinit var binding : ActivityHotboardSearchBinding
    var key_word : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotboardSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.hotboardSearchMainBackIv.setOnClickListener {
            finish()
        }

        binding.hotboardSearchBtnIv.setOnClickListener {
            val keyWord = binding.hotboardSearchBarEt.text.toString()
            val searchType = binding.hotboardSearchTypeTv.text.toString()

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