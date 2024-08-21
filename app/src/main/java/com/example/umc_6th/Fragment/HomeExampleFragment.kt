package com.example.umc_6th.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_6th.Activity.HomeExampleActivity
import com.example.umc_6th.ExampleFragment
import com.example.umc_6th.HomeSearchActivity
import com.example.umc_6th.MainActivity
import com.example.umc_6th.R
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.FindAllFavoriteResponse
import com.example.umc_6th.Retrofit.Request.majorExampleRequest
import com.example.umc_6th.Retrofit.Response.RegisterFavoriteExampleResponse
import com.example.umc_6th.Retrofit.Response.getExampleResponse
import com.example.umc_6th.databinding.FragmentHomeExampleBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeExampleFragment: Fragment() {
    lateinit var binding: FragmentHomeExampleBinding



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeExampleBinding.inflate(inflater, container, false)

        initFragment()

        binding.homeExampleStarIv.isSelected = false

        binding.homeExampleSearchWordTv.text = HomeExampleActivity.example_tag
        binding.homeExampleContentQuizTv.text = HomeExampleActivity.example

        if(HomeExampleActivity.favorite_id != 0){
            binding.homeExampleStarIv.setImageResource(R.drawable.ic_star_on)
        }


        binding.exampleAnotherExampleCl.setOnClickListener {
            callNewExample()
        }

        binding.exampleAnswerCl.setOnClickListener {
            (activity as HomeExampleActivity).supportFragmentManager.beginTransaction()
                .setCustomAnimations(
//                    R.animator.card_flip_in,  // 애니메이션 들어올 때
//                    R.animator.card_flip_out, // 애니메이션 나갈 때
//                    R.animator.card_flip_in,  // 뒤로 가기 할 때 들어올 때
//                    R.animator.card_flip_out  // 뒤로 가기 할 때 나갈 때
                    R.animator.card_front,
                    R.animator.card_back,
                    R.animator.card_front,
                    R.animator.card_back
                )
                .replace(R.id.home_example_main_frm, HomeAnswerFragment()).commitAllowingStateLoss()
        }

        binding.homeExampleStarIv.setOnClickListener {
            if(!binding.homeExampleStarIv.isSelected){
                CookieClient.service.getBookmarks(MainActivity.accessToken).enqueue(object : Callback<FindAllFavoriteResponse>{
                    override fun onResponse(
                        call: Call<FindAllFavoriteResponse>,
                        response: Response<FindAllFavoriteResponse>
                    ) {
                        Log.d("retrofit/Example_favorite", response.toString())
                        if(response.body()?.result != null){
                            val result = response.body()?.result
                            val list = result!!.map { it.exampleId }
                            Log.d("retrofit/Example_favorite/list",list.toString())
                            if(HomeExampleActivity.example_id in list){
                                binding.homeExampleStarIv.setImageResource(R.drawable.ic_star_on)
                            } else {
                                CookieClient.service.postBookmark(MainActivity.accessToken, HomeExampleActivity.example_id)
                                    .enqueue(object : Callback<RegisterFavoriteExampleResponse> {
                                        override fun onResponse(
                                            call: Call<RegisterFavoriteExampleResponse>,
                                            response: Response<RegisterFavoriteExampleResponse>
                                        ) {
                                            Log.d("retrofit/Example_favorite", response.toString())
                                            if(response.body()?.result == 200){
                                                binding.homeExampleStarIv.setImageResource(R.drawable.ic_star_on)
                                                binding.homeExampleStarIv.isSelected = true
                                                Log.d("retrofit/Example_favorite", response.body().toString())
                                            }
                                        }

                                        override fun onFailure(
                                            call: Call<RegisterFavoriteExampleResponse>,
                                            t: Throwable
                                        ) {
                                            Log.e("retrofit/Example_favorite",t.toString())
                                        }
                                    })
                            }
                        }
                    }

                    override fun onFailure(call: Call<FindAllFavoriteResponse>, t: Throwable) {
                        Log.e("retrofit/Example_favorite",t.toString())
                    }
                })
            }
        }

        return binding.root
    }

    private fun callNewExample() {
        val request = majorExampleRequest(
            majorId = HomeSearchActivity.major_id,
            question = HomeSearchActivity.text
        )

        CookieClient.service.postMajorFind(MainActivity.accessToken, request).enqueue(object :
            Callback<getExampleResponse>{
            override fun onResponse(
                call: Call<getExampleResponse>,
                response: Response<getExampleResponse>
            ) {
                val result = response.body()?.result
                Log.d("retrofit/example_search",response.toString())

                if(result != null) {
                    HomeExampleActivity.example_id = result.exampleId
                    HomeExampleActivity.example = result.exampleQuestion
                    HomeExampleActivity.answer = result.correctAnswer

                    initExample()
                }
            }

            override fun onFailure(call: Call<getExampleResponse>, t: Throwable) {
                Log.e("retrofit/example", t.toString())
            }
        })
    }

    private fun initExample() {
        (activity as HomeExampleActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.home_example_main_frm, HomeExplainFragment()).commitAllowingStateLoss()
    }
    override fun onStart() {
        super.onStart()
        HomeExampleActivity.frag = 2
    }

    private fun initFragment(){
        CookieClient.service.getBookmarks(MainActivity.accessToken).enqueue(object : Callback<FindAllFavoriteResponse>{
            override fun onResponse(
                call: Call<FindAllFavoriteResponse>,
                response: Response<FindAllFavoriteResponse>
            ) {
                Log.d("retrofit/Example_favorite", response.toString())
                if(response.body()?.result != null){
                    val result = response.body()?.result
                    val list = result!!.map { it.exampleId }
                    Log.d("retrofit/Example_favorite/list",list.toString())
                    if(HomeExampleActivity.example_id in list){
                        binding.homeExampleStarIv.setImageResource(R.drawable.ic_star_on)
                        binding.homeExampleStarIv.isSelected = true
                    }
                }
            }

            override fun onFailure(call: Call<FindAllFavoriteResponse>, t: Throwable) {
                Log.e("retrofit/Example_favorite",t.toString())
            }
        })
    }
}