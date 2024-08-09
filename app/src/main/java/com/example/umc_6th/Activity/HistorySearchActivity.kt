package com.example.umc_6th.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.umc_6th.ConfigHistoryFragment
import com.example.umc_6th.Fragment.ConfigHistorySearchResultFragment
import com.example.umc_6th.MainActivity
import com.example.umc_6th.Retrofit.BoardSearchMajorResponse
import com.example.umc_6th.Retrofit.HistoryResponse
import com.example.umc_6th.Retrofit.RetrofitClient
import com.example.umc_6th.databinding.ActivityCommunitySearchBinding
import com.example.umc_6th.databinding.ActivityHistorySearchBinding
import retrofit2.Call
import retrofit2.Response

class HistorySearchActivity : AppCompatActivity() {

    lateinit var binding : ActivityHistorySearchBinding
    private var page : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistorySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.historySearchMainBackIv.setOnClickListener {
            finish()
        }

        val key_word : String = binding.historySearchBarEt.text.toString()

        binding.historySearchBtnIv.setOnClickListener {
            when (binding.historySearchTypeTv.text) {
                "내가 쓴 글" -> {
                    searchBoard(key_word)
                }
                "댓글단 글" -> {
                    searchComment(key_word)
                }
                "좋아요" -> {
                    searchLike(key_word)
                }
                "전체" -> {
                    searchAll(key_word)
                }
            }
        }

        setupDropdown()
    }

    private fun searchAll(key_word : String) {
        RetrofitClient.service.getHistorySearch(MainActivity.accessToken,page,key_word).enqueue(object :
            retrofit2.Callback<HistoryResponse> {
            override fun onFailure(call: Call<HistoryResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<HistoryResponse>?,
                response: Response<HistoryResponse>?
            ) {
                Log.d("retrofit", response.toString())
                Log.d("retrofit", response?.code().toString())
                Log.d("retrofit", response?.body().toString())
                Log.d("retrofit", response?.message().toString())
                Log.d("retrofit", response?.body()?.result.toString())
            }
        })
    }

    private fun searchBoard(key_word : String) {
        RetrofitClient.service.getMyBoardsSearch(MainActivity.accessToken,page,key_word).enqueue(object :
            retrofit2.Callback<HistoryResponse> {
            override fun onFailure(call: Call<HistoryResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<HistoryResponse>?,
                response: Response<HistoryResponse>?
            ) {
                Log.d("retrofit", response.toString())
                Log.d("retrofit", response?.code().toString())
                Log.d("retrofit", response?.body().toString())
                Log.d("retrofit", response?.message().toString())
                Log.d("retrofit", response?.body()?.result.toString())
            }
        })
    }
    private fun searchComment(key_word : String) {
        RetrofitClient.service.getMyCommentsSearch(MainActivity.accessToken,page,key_word).enqueue(object :
            retrofit2.Callback<HistoryResponse> {
            override fun onFailure(call: Call<HistoryResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<HistoryResponse>?,
                response: Response<HistoryResponse>?
            ) {
                Log.d("retrofit", response.toString())
                Log.d("retrofit", response?.code().toString())
                Log.d("retrofit", response?.body().toString())
                Log.d("retrofit", response?.message().toString())
                Log.d("retrofit", response?.body()?.result.toString())
            }
        })
    }
    private fun searchLike(key_word : String) {
        RetrofitClient.service.getMyLikesSeach(MainActivity.accessToken,page,key_word).enqueue(object :
            retrofit2.Callback<HistoryResponse> {
            override fun onFailure(call: Call<HistoryResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<HistoryResponse>?,
                response: Response<HistoryResponse>?
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
        binding.historySearchTypeOptionBtnCl.setOnClickListener {
            if(binding.historySearchDropdownCl.visibility == View.GONE) {
                binding.historySearchDropdownCl.visibility = View.VISIBLE
            } else {
                binding.historySearchDropdownCl.visibility = View.GONE
            }
        }

        binding.historySearchDropdownTitleCl.setOnClickListener {
            binding.historySearchTypeTv.text = "내가 쓴 글"
            binding.historySearchDropdownCl.visibility = View.GONE
            binding.historySearchTitleTv.setTypeface(null,android.graphics.Typeface.BOLD)
            binding.historySearchContentTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.historySearchBothTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.historySearchUsernameTv.setTypeface(null,android.graphics.Typeface.NORMAL)
        }

        binding.historySearchDropdownContentCl.setOnClickListener {
            binding.historySearchTypeTv.text = "댓글단 글"
            binding.historySearchDropdownCl.visibility = View.GONE
            binding.historySearchContentTv.setTypeface(null,android.graphics.Typeface.BOLD)
            binding.historySearchTitleTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.historySearchBothTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.historySearchUsernameTv.setTypeface(null,android.graphics.Typeface.NORMAL)
        }

        binding.historySearchDropdownBothCl.setOnClickListener {
            binding.historySearchTypeTv.text = "좋아요"
            binding.historySearchDropdownCl.visibility = View.GONE
            binding.historySearchBothTv.setTypeface(null,android.graphics.Typeface.BOLD)
            binding.historySearchTitleTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.historySearchContentTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.historySearchUsernameTv.setTypeface(null,android.graphics.Typeface.NORMAL)
        }

        binding.historySearchDropdownUsernameCl.setOnClickListener {
            binding.historySearchTypeTv.text = "전체"
            binding.historySearchDropdownCl.visibility = View.GONE
            binding.historySearchUsernameTv.setTypeface(null,android.graphics.Typeface.BOLD)
            binding.historySearchTitleTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.historySearchContentTv.setTypeface(null,android.graphics.Typeface.NORMAL)
            binding.historySearchBothTv.setTypeface(null,android.graphics.Typeface.NORMAL)
        }
    }
}