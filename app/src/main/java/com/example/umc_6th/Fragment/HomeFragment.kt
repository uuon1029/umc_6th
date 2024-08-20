package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_6th.Activity.HomeExampleActivity
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.Response.MainPageRes
import com.example.umc_6th.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        callGetMainHomeBoard()
        return binding.root
    }

    private fun initSetOnClickListener(list:ArrayList<MainPageRes.Result>) {
        binding.homeQuestionBg1Cl.setOnClickListener {
            val intent = Intent(activity, HomeExampleActivity::class.java)
            HomeExampleActivity.answer_id = list[0].answerId
            startActivity(intent)
        }
        binding.homeExampleBgCl.setOnClickListener {
            val intent = Intent(activity, HomeExampleActivity::class.java)
            HomeExampleActivity.answer_id = list[1].answerId
            startActivity(intent)
        }
    }

    private fun callGetMainHomeBoard() {
        CookieClient.service.getMainHomeBoard(MainActivity.accessToken).enqueue(object :
            Callback<MainPageRes> {
            override fun onFailure(call: Call<MainPageRes>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<MainPageRes>,
                response: Response<MainPageRes>
            ) {
                if (response.isSuccessful && response.body()?.isSuccess == true) {
                    Log.d("retrofit_home_quest",response.toString())
                    val mainBoardList = response.body()?.result
                    if (!mainBoardList.isNullOrEmpty()) {

                        val latestQuestion = mainBoardList[0]
                        binding.homeQuestionTitleTv.text = latestQuestion.question
                        binding.homeQuestionMajorTv.text = latestQuestion.major
                        binding.homeQuestionDateTv.text = latestQuestion.createdAt
                        binding.homeQuestionNameTv.text = latestQuestion.nickname
                        binding.homeQuestionBodyTv.text = latestQuestion.content
                        Log.d("retrofit_home_quest",mainBoardList.toString())


                        if (mainBoardList.size > 1) {
                            val exampleQuestion = mainBoardList[1]
                            binding.homeExampleTitleTv.text = exampleQuestion.question
                            binding.homeExampleMajorTv.text = exampleQuestion.major
                            binding.homeExampleDateTv.text = exampleQuestion.createdAt
                            binding.homeExampleNameTv.text = exampleQuestion.nickname
                            binding.homeExampleBodyTv.text = exampleQuestion.content
                            Log.d("retrofit_home_ex",mainBoardList.toString())
                        }
                        initSetOnClickListener(mainBoardList)
                    }
                } else {
                    Log.e("retrofit", "Response error: ${response.errorBody()?.string()}")
                }
            }
        })
    }
}
