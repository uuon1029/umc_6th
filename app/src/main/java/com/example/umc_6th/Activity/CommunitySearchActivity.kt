package com.example.umc_6th.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.umc_6th.MainActivity
import com.example.umc_6th.MoreTotalBoardFragment
import com.example.umc_6th.R
import com.example.umc_6th.Retrofit.RetrofitClient
import com.example.umc_6th.Retrofit.BoardSearchMajorResponse
import com.example.umc_6th.databinding.ActivityCommunitySearchBinding
import retrofit2.Call
import retrofit2.Response
import java.util.zip.Inflater
import javax.security.auth.callback.Callback

class CommunitySearchActivity : AppCompatActivity() {

    lateinit var binding : ActivityCommunitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.commuSearchMainBackIv.setOnClickListener {
            finish()
        }

        val major_id : Int = 2
        val key_word : String = binding.commuSearchBarEt.text.toString()

        binding.commuSearchBtnIv.setOnClickListener {
            if (key_word != null) {
                searchTitle(major_id, key_word)
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