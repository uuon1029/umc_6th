package com.example.umc_6th.Activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.umc_6th.databinding.ActivityCommunitySearchBinding
import com.example.umc_6th.databinding.ActivityMajorSearchBinding

class MajorSearchActivity : AppCompatActivity() {

    lateinit var binding : ActivityMajorSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMajorSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.majorSearchMainBackIv.setOnClickListener {
            finish()
        }

        setupDropdown()
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