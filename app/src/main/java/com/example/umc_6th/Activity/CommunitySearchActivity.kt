package com.example.umc_6th.Activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.MainActivity
import com.example.umc_6th.MoreTotalBoardFragment
import com.example.umc_6th.R
import com.example.umc_6th.databinding.ActivityCommunitySearchBinding
import java.util.zip.Inflater

class CommunitySearchActivity : AppCompatActivity() {

    lateinit var binding : ActivityCommunitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.commuSearchMainBackIv.setOnClickListener {
            finish()
        }

        setupDropdown()
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