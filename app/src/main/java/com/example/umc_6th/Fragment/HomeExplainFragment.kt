package com.example.umc_6th.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_6th.Activity.HomeExampleActivity
import com.example.umc_6th.R
import com.example.umc_6th.databinding.FragmentHomeExplainBinding

class HomeExplainFragment: Fragment() {
    lateinit var binding: FragmentHomeExplainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeExplainBinding.inflate(inflater, container, false)


        binding.homeExplainTitleTv.text = HomeExampleActivity.example_tag
        binding.homeExplainBodyTv.text = HomeExampleActivity.content

        binding.explainAnswerTv.setOnClickListener {
            (activity as HomeExampleActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.home_example_main_frm, HomeExampleFragment()).commitAllowingStateLoss()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        HomeExampleActivity.frag = 1
    }
}