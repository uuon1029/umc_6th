package com.example.umc_6th.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.umc_6th.Retrofit.BoardSearchMajorResponse
import com.example.umc_6th.Retrofit.RetrofitClient
import com.example.umc_6th.databinding.ActivityCommunitySearchBinding
import com.example.umc_6th.databinding.ActivityMajorSearchBinding
import retrofit2.Call
import retrofit2.Response

class MajorSearchActivity : AppCompatActivity() {

    lateinit var binding : ActivityMajorSearchBinding
    val major_id : Int = 2
    var key_word : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMajorSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.majorSearchMainBackIv.setOnClickListener {
            finish()
        }

        binding.majorSearchBtnIv.setOnClickListener {
            key_word = binding.majorSearchBarEt.text.toString()
            when (binding.majorSearchTypeTv.text) {
                "제목" -> {
                    searchTitle(major_id, key_word)
                }
                "내용" -> {
                    searchContent(major_id, key_word)
                }
                "제목+내용" -> {
                    searchAll(major_id, key_word)
                }
                "글쓴이" -> {
                    searchUser(major_id, key_word)
                }
            }
        }

        setupDropdown()
    }
    private fun searchTitle(major_id : Int, key_word : String, page : Int = 0) {
        RetrofitClient.service.getBoardMajorSearchTitle(major_id, key_word, page).enqueue(object :
            retrofit2.Callback<BoardSearchMajorResponse> {
            override fun onFailure(call: Call<BoardSearchMajorResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardSearchMajorResponse>?,
                response: Response<BoardSearchMajorResponse>?
            ) {
                Log.d("retrofit", response.toString())
                Log.d("retrofit", response?.code().toString())
                Log.d("retrofit", response?.body().toString())
                Log.d("retrofit", response?.message().toString())
                Log.d("retrofit", response?.body()?.result.toString())
            }
        })
    }
    private fun searchContent(major_id : Int, key_word : String, page : Int = 0) {
        RetrofitClient.service.getBoardMajorSearchContent(major_id, key_word, page).enqueue(object :
            retrofit2.Callback<BoardSearchMajorResponse> {
            override fun onFailure(call: Call<BoardSearchMajorResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardSearchMajorResponse>?,
                response: Response<BoardSearchMajorResponse>?
            ) {
                Log.d("retrofit", response.toString())
                Log.d("retrofit", response?.code().toString())
                Log.d("retrofit", response?.body().toString())
                Log.d("retrofit", response?.message().toString())
                Log.d("retrofit", response?.body()?.result.toString())
            }
        })
    }
    private fun searchAll(major_id : Int, key_word : String, page : Int = 0) {
        RetrofitClient.service.getBoardMajorSearch(major_id, key_word, page).enqueue(object :
            retrofit2.Callback<BoardSearchMajorResponse> {
            override fun onFailure(call: Call<BoardSearchMajorResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardSearchMajorResponse>?,
                response: Response<BoardSearchMajorResponse>?
            ) {
                Log.d("retrofit", response.toString())
                Log.d("retrofit", response?.code().toString())
                Log.d("retrofit", response?.body().toString())
                Log.d("retrofit", response?.message().toString())
                Log.d("retrofit", response?.body()?.result.toString())
            }
        })
    }
    private fun searchUser(major_id : Int, key_word : String, page : Int = 0) {
        RetrofitClient.service.getBoardMajorSearchUser(major_id, key_word, page).enqueue(object :
            retrofit2.Callback<BoardSearchMajorResponse> {
            override fun onFailure(call: Call<BoardSearchMajorResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardSearchMajorResponse>?,
                response: Response<BoardSearchMajorResponse>?
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