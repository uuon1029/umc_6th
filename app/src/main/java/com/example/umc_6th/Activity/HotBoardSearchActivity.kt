package com.example.umc_6th.Activity

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
            key_word = binding.hotboardSearchBarEt.text.toString()
            when (binding.hotboardSearchTypeTv.text) {
                "제목" -> {
                    searchTitle(key_word)
                }
                "내용" -> {
                    searchContent(key_word)
                }
                "제목+내용" -> {
                    searchAll(key_word)
                }
                "글쓴이" -> {
                    searchUser(key_word)
                }
            }
        }

        setupDropdown()
    }
    private fun searchTitle(key_word : String, page : Int = 0) {
        RetrofitClient.service.getBoardHotSearchTitle(key_word, page).enqueue(object :
            retrofit2.Callback<BoardSearchHotResponse> {
            override fun onFailure(call: Call<BoardSearchHotResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardSearchHotResponse>?,
                response: Response<BoardSearchHotResponse>?
            ) {
                Log.d("retrofit", response.toString())
                Log.d("retrofit", response?.code().toString())
                Log.d("retrofit", response?.body().toString())
                Log.d("retrofit", response?.message().toString())
                Log.d("retrofit", response?.body()?.result.toString())
            }
        })
    }
    private fun searchContent(key_word : String, page : Int = 0) {
        RetrofitClient.service.getBoardHotSearchContent(key_word, page).enqueue(object :
            retrofit2.Callback<BoardSearchHotResponse> {
            override fun onFailure(call: Call<BoardSearchHotResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardSearchHotResponse>?,
                response: Response<BoardSearchHotResponse>?
            ) {
                Log.d("retrofit", response.toString())
                Log.d("retrofit", response?.code().toString())
                Log.d("retrofit", response?.body().toString())
                Log.d("retrofit", response?.message().toString())
                Log.d("retrofit", response?.body()?.result.toString())
            }
        })
    }
    private fun searchAll(key_word : String, page : Int = 0) {
        RetrofitClient.service.getBoardHotSearch(key_word, page).enqueue(object :
            retrofit2.Callback<BoardSearchHotResponse> {
            override fun onFailure(call: Call<BoardSearchHotResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardSearchHotResponse>?,
                response: Response<BoardSearchHotResponse>?
            ) {
                Log.d("retrofit", response.toString())
                Log.d("retrofit", response?.code().toString())
                Log.d("retrofit", response?.body().toString())
                Log.d("retrofit", response?.message().toString())
                Log.d("retrofit", response?.body()?.result.toString())
            }
        })
    }
    private fun searchUser(key_word : String, page : Int = 0) {
        RetrofitClient.service.getBoardHotSearchUser(key_word, page).enqueue(object :
            retrofit2.Callback<BoardSearchHotResponse> {
            override fun onFailure(call: Call<BoardSearchHotResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardSearchHotResponse>?,
                response: Response<BoardSearchHotResponse>?
            ) {
                Log.d("retrofit", response.toString())
                Log.d("retrofit", response?.code().toString())
                Log.d("retrofit", response?.body().toString())
                Log.d("retrofit", response?.message().toString())
                Log.d("retrofit", response?.body()?.result.toString())
            }
        })
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