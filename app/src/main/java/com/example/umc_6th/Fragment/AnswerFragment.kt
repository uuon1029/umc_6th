package com.example.umc_6th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_6th.databinding.FragmentAnswerBinding

class AnswerFragment : Fragment() {

    lateinit var binding : FragmentAnswerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnswerBinding.inflate(inflater,container,false)

        binding.answerExampleCl.setOnClickListener {
            (context as SearchResultActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.search_result_main_frm,ExampleFragment())
                .commitAllowingStateLoss()
        }

        return binding.root
    }
}