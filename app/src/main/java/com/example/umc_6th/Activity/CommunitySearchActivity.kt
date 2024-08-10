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
import com.example.umc_6th.MainActivity
import com.example.umc_6th.MoreTotalBoardFragment
import com.example.umc_6th.R
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
    var key_word : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.commuSearchMainBackIv.setOnClickListener {
            finish()
        }

        binding.commuSearchBtnIv.setOnClickListener {
            val keyWord = binding.commuSearchBarEt.text.toString()
            val searchType = binding.commuSearchTypeTv.text.toString()

            val spf = getSharedPreferences("search",Context.MODE_PRIVATE)
            with(spf.edit()) {
                putString("key_word",keyWord)
                putString("search_type",searchType)
                apply()
            }
            setResult(Activity.RESULT_OK)
            finish()
//            key_word = binding.commuSearchBarEt.text.toString()
//            when (binding.commuSearchTypeTv.text) {
//                "제목" -> {
//                    searchTitle(key_word)
//                }
//                "내용" -> {
//                    searchContent(key_word)
//                }
//                "제목+내용" -> {
//                    searchAll(key_word)
//                }
//                "글쓴이" -> {
//                    searchUser(key_word)
//                }
//            }
        }

        setupDropdown()
    }
    private fun searchTitle(key_word : String, page : Int = 0) {
        RetrofitClient.service.getBoardAllSearchTitle(key_word, page).enqueue(object :
            retrofit2.Callback<BoardSearchAllResponse> {
            override fun onFailure(call: Call<BoardSearchAllResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardSearchAllResponse>?,
                response: Response<BoardSearchAllResponse>?
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
        RetrofitClient.service.getBoardAllSearchContent(key_word, page).enqueue(object :
            retrofit2.Callback<BoardSearchAllResponse> {
            override fun onFailure(call: Call<BoardSearchAllResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardSearchAllResponse>?,
                response: Response<BoardSearchAllResponse>?
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
        RetrofitClient.service.getBoardAllSearch(key_word, page).enqueue(object :
            retrofit2.Callback<BoardSearchAllResponse> {
            override fun onFailure(call: Call<BoardSearchAllResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardSearchAllResponse>?,
                response: Response<BoardSearchAllResponse>?
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
        RetrofitClient.service.getBoardAllSearchUser(key_word, page).enqueue(object :
            retrofit2.Callback<BoardSearchAllResponse> {
            override fun onFailure(call: Call<BoardSearchAllResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardSearchAllResponse>?,
                response: Response<BoardSearchAllResponse>?
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