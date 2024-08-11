package com.example.umc_6th

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.Request.majorExampleRequest
import com.example.umc_6th.Retrofit.Response.getExampleResponse
import com.example.umc_6th.databinding.FragmentExplainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExplainFragment : Fragment() {
    lateinit var binding: FragmentExplainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExplainBinding.inflate(inflater,container,false)

        binding.explainAnswerCl.setOnClickListener {
            (context as SearchResultActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.search_result_main_frm,ExampleFragment())
                .commitAllowingStateLoss()
        }

        val spf = activity?.getSharedPreferences("searchText",Context.MODE_PRIVATE)
        val inputText = spf?.getString("input_text","")
        Log.d("result",inputText.toString())
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
//                binding.explainSearchWordTv.text = response.body()?.result?.question!!
//                binding.explainFullWordTv.text = response.body()?.result?.question!!
//                binding.explainContentQuizTv.text = response.body()?.result?.answer!!
                Log.d("result_get",response.body().toString())
            }

            override fun onFailure(call: Call<getExampleResponse>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }

        })
    }

}