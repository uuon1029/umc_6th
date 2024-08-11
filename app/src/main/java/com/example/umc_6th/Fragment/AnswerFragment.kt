package com.example.umc_6th

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_6th.Activity.WriteActivity
import com.example.umc_6th.databinding.FragmentAnswerBinding

class AnswerFragment : Fragment() {

    lateinit var binding : FragmentAnswerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnswerBinding.inflate(inflater,container,false)

        val spf2 = activity?.getSharedPreferences("example", Context.MODE_PRIVATE)

        val receiveWord = spf2!!.getString("example_word","")
        val receiveAnswer = spf2!!.getString("example_answer","")

        binding.answerSearchWordTv.text = receiveWord
        binding.answerContentQuizTv.text = receiveAnswer

        binding.answerExampleCl.setOnClickListener {
            (context as SearchResultActivity).supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.animator.card_flip_in,  // 애니메이션 들어올 때
                    R.animator.card_flip_out, // 애니메이션 나갈 때
                    R.animator.card_flip_in,  // 뒤로 가기 할 때 들어올 때
                    R.animator.card_flip_out  // 뒤로 가기 할 때 나갈 때
                )
                .replace(R.id.search_result_main_frm, ExampleFragment())
                .commitAllowingStateLoss()
        }

        binding.answerWriteBtnIv.setOnClickListener {
            val intent = Intent(activity, WriteActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}