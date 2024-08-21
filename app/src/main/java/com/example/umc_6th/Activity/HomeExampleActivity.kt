package com.example.umc_6th.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.umc_6th.ExampleFragment
import com.example.umc_6th.Fragment.HomeExampleFragment
import com.example.umc_6th.Fragment.HomeExplainFragment
import com.example.umc_6th.HomeSearchActivity
import com.example.umc_6th.MainActivity
import com.example.umc_6th.R
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.Response.GetExampleByIdResponse
import com.example.umc_6th.Retrofit.Response.MajorAnswerResponse
import com.example.umc_6th.Retrofit.Response.RegisterFavoriteExampleResponse
import com.example.umc_6th.Retrofit.Response.getExampleBoardResponse
import com.example.umc_6th.databinding.ActivityHomeExampleBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeExampleActivity : AppCompatActivity() {
    lateinit var binding : ActivityHomeExampleBinding

    companion object {
        var favorite_id = 0
        var example_id = 0
        var answer_id = 0

        var example_tag : String?= ""
        var question : String? =""
        var content : String? = ""
        var example : String? = ""
        var answer : String? = ""
        var fullExample : String = ""

        var frag = 0 // 1 - Explain, 2 - Example, 3 - Answer
        var homeExampleActivity : HomeExampleActivity? = null
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this,R.color.main_color)

        initAnswerId()
        binding.homeExampleSearchBtnIv.setOnClickListener {
            val i = Intent(this, HomeSearchActivity::class.java)
            startActivity(i)
        }

        binding.homeExamplePrevBtnIv.setOnClickListener {
            when(frag) {
                2,3 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.home_example_main_frm,HomeExplainFragment()).commitAllowingStateLoss()
                }
                else -> {
                    finish()
                }
            }
        }
        homeExampleActivity = this
    }

    private fun initFragment() {
        Log.d("HomeExample", listOf(favorite_id, example_id).toString())
        CookieClient.service.getExample(example_id).enqueue(object : Callback<getExampleBoardResponse>{
            override fun onResponse(
                call: Call<getExampleBoardResponse>,
                response: Response<getExampleBoardResponse>
            ) {
                val result = response.body()?.result
                Log.d("retrofit/Example",result.toString())
                if(result != null){
                    example_tag = result.tag
                    example = result.problem
                    answer = result.answer
                    initStatus()
                    Log.d("retrofit/Example_id", example_id.toString())
                }
            }

            override fun onFailure(call: Call<getExampleBoardResponse>, t: Throwable) {
                Log.e("retrofit/Example",t.toString())
            }
        })
    }

    private fun initAnswerId(){
        example_id = 0
        if(answer_id != 0) {
            Log.d("retrofit","")
            CookieClient.service.getMajorAnswer(MainActivity.accessToken, answer_id).enqueue(object : Callback<MajorAnswerResponse>{
                override fun onResponse(
                    call: Call<MajorAnswerResponse>,
                    response: Response<MajorAnswerResponse>
                ) {
                    Log.d("retrofit/Example_getAnswer",response.toString())
                    if(response.body()?.result != null){
                        val result = response.body()?.result
                        example_id = result!!.exampleId
                        content = result.content
                        initFragment()
                    }

                }

                override fun onFailure(call: Call<MajorAnswerResponse>, t: Throwable) {
                    Log.e("retrofit/Example_getAnswer", t.toString())
                }
            })
        }
        initFragment()
    }

    private fun initStatus(){
        binding.homeExampleTitleWordTv.text =
            if(example_tag!!.length < 20){example_tag} else{example_tag!!.substring(0,20)+"..."}
        if(favorite_id == 0) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.home_example_main_frm,HomeExplainFragment()).commitAllowingStateLoss()
        }
        else {
            favorite_id = 0
            supportFragmentManager.beginTransaction()
                .replace(R.id.home_example_main_frm, HomeExampleFragment()).commitAllowingStateLoss()
        }
    }
}