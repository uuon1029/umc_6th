package com.example.umc_6th.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_6th.Activity.HomeExampleActivity
import com.example.umc_6th.R
import com.example.umc_6th.databinding.FragmentHomeAnswerBinding
import com.example.umc_6th.databinding.FragmentHomeExplainBinding

class HomeAnswerFragment: Fragment() {
    lateinit var binding: FragmentHomeAnswerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeAnswerBinding.inflate(inflater, container, false)

        binding.homeAnswerSearchWordTv.text = HomeExampleActivity.example_tag
        binding.homeAnswerContentQuizTv.text = HomeExampleActivity.content

        binding.homeAnswerExampleCl.setOnClickListener {
            (activity as HomeExampleActivity).supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.animator.card_flip_in,  // 애니메이션 들어올 때
                    R.animator.card_flip_out, // 애니메이션 나갈 때
                    R.animator.card_flip_in,  // 뒤로 가기 할 때 들어올 때
                    R.animator.card_flip_out  // 뒤로 가기 할 때 나갈 때
                )
                .replace(R.id.home_example_main_frm, HomeExampleFragment()).commitAllowingStateLoss()
        }

        return binding.root
    }
}