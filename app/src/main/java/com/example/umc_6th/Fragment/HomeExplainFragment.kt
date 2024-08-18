package com.example.umc_6th.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_6th.Activity.HomeExampleActivity
import com.example.umc_6th.databinding.FragmentHomeExplainBinding

class HomeExplainFragment: Fragment() {
    lateinit var binding: FragmentHomeExplainBinding

    val example_id = HomeExampleActivity.example_id
    val tag = HomeExampleActivity.tag
    val example = HomeExampleActivity.example
    val answer = HomeExampleActivity.answer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeExplainBinding.inflate(inflater, container, false)

        binding.homeExplainTitleTv.text = tag
        binding.homeExplainBodyTv.text =

        return binding.root
    }
}