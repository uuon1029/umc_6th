package com.example.umc_6th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_6th.databinding.FragmentExampleBinding

class ExampleFragment : Fragment() {

    lateinit var binding: FragmentExampleBinding

    private var isMarked:Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExampleBinding.inflate(inflater,container,false)

        binding.exampleStarIv.setOnClickListener {
            if(isMarked) {
                binding.exampleStarIv.setImageResource(R.drawable.ic_bookmark_off)
            } else {
                binding.exampleStarIv.setImageResource(R.drawable.ic_bookmark_on)
            }

            isMarked = !isMarked
        }

        binding.exampleAnswerCl.setOnClickListener {
            (context as SearchResultActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.search_result_main_frm,AnswerFragment())
                .commitAllowingStateLoss()
        }

        return binding.root
    }
}