package com.example.umc_6th

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.Request.exampleRegisterRequest
import com.example.umc_6th.Retrofit.Request.majorExampleRequest
import com.example.umc_6th.Retrofit.Response.exampleRegisterResponse
import com.example.umc_6th.Retrofit.Response.getExampleResponse
import com.example.umc_6th.databinding.FragmentExampleBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExampleFragment : Fragment() {

    lateinit var binding: FragmentExampleBinding

    private var isMarked:Boolean = false
    private var accessToken = MainActivity.accessToken

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExampleBinding.inflate(inflater,container,false)

        binding.exampleStarIv.setOnClickListener {
            if(isMarked) {
                binding.exampleStarIv.setImageResource(R.drawable.ic_star_off)
            } else {
                binding.exampleStarIv.setImageResource(R.drawable.ic_star_on)
            }

            isMarked = !isMarked
        }

//        binding.exampleAnswerCl.setOnClickListener {
//            (context as SearchResultActivity).supportFragmentManager.beginTransaction()
//                .replace(R.id.search_result_main_frm,AnswerFragment())
//                .commitAllowingStateLoss()
//        }

        val spf2 = activity?.getSharedPreferences("example",Context.MODE_PRIVATE)

        val receiveWord = spf2!!.getString("example_word","")
        val receiveQuiz = spf2!!.getString("example_quiz","")

        binding.exampleSearchWordTv.text = receiveWord
        binding.exampleContentQuizTv.text = receiveQuiz

        binding.exampleAnotherExampleCl.setOnClickListener {
            (context as SearchResultActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.search_result_main_frm, ExplainFragment())
                .commitAllowingStateLoss()
        }

        binding.exampleAnswerCl.setOnClickListener {
            (context as SearchResultActivity).supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.animator.card_flip_in,  // 애니메이션 들어올 때
                    R.animator.card_flip_out, // 애니메이션 나갈 때
                    R.animator.card_flip_in,  // 뒤로 가기 할 때 들어올 때
                    R.animator.card_flip_out  // 뒤로 가기 할 때 나갈 때
                )
                .replace(R.id.search_result_main_frm, AnswerFragment())
                .commitAllowingStateLoss()
        }


        return binding.root
    }
}