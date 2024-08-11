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

        val spf = activity?.getSharedPreferences("searchText", Context.MODE_PRIVATE)
        val inputText = spf?.getString("input_text","")

        if(inputText != null) {
            searchExample(inputText)
        }




        return binding.root
    }

    private fun searchExample(inputText: String) {
        val request = majorExampleRequest(
            question = inputText
        )

        CookieClient.service.postMajorFind(request).enqueue(object : Callback<getExampleResponse> {
            override fun onResponse(
                call: Call<getExampleResponse>,
                response: Response<getExampleResponse>
            ) {
                Log.d("result", response.body()?.result!!.toString())
                val word = response.body()?.result?.question!!
                val question = response.body()?.result?.exampleQuestion!!
                val answer = response.body()?.result?.correctAnswer!!
                searchExampleQuestion(word,question, answer)
            }

            override fun onFailure(call: Call<getExampleResponse>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }

        })
    }

    private fun searchExampleQuestion(word: String,question: String,answer: String) {
        val request = exampleRegisterRequest(
            tag = word,
            exampleQuestion = question,
            correctAnswer = answer
        )

        CookieClient.service.postMajorExample(request).enqueue(object  : Callback<exampleRegisterResponse> {
            override fun onResponse(
                call: Call<exampleRegisterResponse>,
                response: Response<exampleRegisterResponse>
            ) {
                binding.exampleSearchWordTv.text = response.body()?.result?.tag!!
                binding.exampleContentQuizTv.text = response.body()?.result?.problem!!
            }

            override fun onFailure(call: Call<exampleRegisterResponse>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }

        })
    }
}