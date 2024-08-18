package com.example.umc_6th.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_6th.Activity.HomeExampleActivity
import com.example.umc_6th.ExampleFragment
import com.example.umc_6th.MainActivity
import com.example.umc_6th.R
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.Response.RegisterFavoriteExampleResponse
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

        binding.homeExampleSearchWordTv.text = HomeExampleActivity.example_tag
        binding.homeExampleContentQuizTv.text = HomeExampleActivity.example

        if(HomeExampleActivity.favorite_id != 0){
            binding.homeExampleStarIv.setImageResource(R.drawable.ic_star_on)
        }


        binding.homeExampleAnswerCl.setOnClickListener {
            (activity as HomeExampleActivity).supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.animator.card_flip_in,  // 애니메이션 들어올 때
                    R.animator.card_flip_out, // 애니메이션 나갈 때
                    R.animator.card_flip_in,  // 뒤로 가기 할 때 들어올 때
                    R.animator.card_flip_out  // 뒤로 가기 할 때 나갈 때
                )
                .replace(R.id.home_example_main_frm, HomeAnswerFragment()).commitAllowingStateLoss()
        }

        binding.homeExampleStarIv.setOnClickListener {
            CookieClient.service.postBookmark(MainActivity.accessToken, ExampleFragment.example_id)
                .enqueue(object : Callback<RegisterFavoriteExampleResponse> {
                    override fun onResponse(
                        call: Call<RegisterFavoriteExampleResponse>,
                        response: Response<RegisterFavoriteExampleResponse>
                    ) {
                        if(response.body()?.result == 200){
                            binding.homeExampleStarIv.setImageResource(R.drawable.ic_star_on)
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

        return binding.root
    }
}