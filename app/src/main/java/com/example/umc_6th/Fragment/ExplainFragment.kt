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
import com.example.umc_6th.databinding.FragmentExplainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExplainFragment : Fragment() {
    lateinit var binding: FragmentExplainBinding
    private var accessToken = MainActivity.accessToken

    var example_question = ""
    var example_problem = ""
    var example_answer = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExplainBinding.inflate(inflater,container,false)


        val spf = activity?.getSharedPreferences("searchText",Context.MODE_PRIVATE)
        val inputText = spf?.getString("input_text",null)
        Log.d("result",inputText.toString())
        if(inputText != null) {
            searchExample(inputText)
        }

        binding.explainAnswerCl.setOnClickListener {
            (context as SearchResultActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.search_result_main_frm,ExampleFragment())
                .commitAllowingStateLoss()
        }

        return binding.root
    }

    private fun searchExample(inputText: String) {
        Log.d("retrofit_Major_find", inputText)
        val request = majorExampleRequest(
            question = inputText
        )

        CookieClient.service.postMajorFind(accessToken,request).enqueue(object : Callback<getExampleResponse> {
            override fun onResponse(
                call: Call<getExampleResponse>,
                response: Response<getExampleResponse>
            ) {
                binding.explainSearchWordTv.text = response.body()?.result?.question!!
                binding.explainFullWordTv.text = response.body()?.result?.question!!
                binding.explainContentQuizTv.text = response.body()?.result?.answer!!
                Log.d("retrofit_result_get",response.body().toString())
                val word = response.body()?.result?.question!!
                val question = response.body()?.result?.exampleQuestion!!
                val answer = response.body()?.result?.correctAnswer!!

                val spf2 = activity?.getSharedPreferences("example",Context.MODE_PRIVATE)
                with(spf2!!.edit()) {
                    putString("example_word",word)
                    putString("example_quiz",question)
                    putString("example_answer",answer)
                    apply()
                }
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
                example_question = response.body()?.result?.tag.toString()
                example_problem = response.body()?.result?.problem.toString()
                example_answer = response.body()?.result?.answer.toString()
                Log.d("result_example",example_question)
                Log.d("result_example",example_problem)
                Log.d("result_example",example_answer)

            }

            override fun onFailure(call: Call<exampleRegisterResponse>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }

        })
    }

}