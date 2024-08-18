package com.example.umc_6th.Activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.Fragment.HomeExplainFragment
import com.example.umc_6th.MainActivity
import com.example.umc_6th.R
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.Response.GetExampleByIdResponse
import com.example.umc_6th.databinding.ActivityHomeExampleBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeExampleActivity : AppCompatActivity() {
    lateinit var binding : ActivityHomeExampleBinding

    companion object {
        var favorite_id = 0
        var example_id = 0
        var tag = ""
        var example = ""
        var answer = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (favorite_id != 0) {
            CookieClient.service.getBookmark(MainActivity.accessToken, favorite_id).enqueue(object :
                Callback<GetExampleByIdResponse>{
                override fun onResponse(
                    call: Call<GetExampleByIdResponse>,
                    response: Response<GetExampleByIdResponse>
                ) {
                    val result = response.body()?.result
                    Log.d("retrofit/Example",result.toString())
                    if(result != null){
                        example_id = result.id
                        tag = result.tag
                        example = result.problem
                        answer = result.answer
                        Log.d("retrofit/Example_id", example_id.toString())
                    }
                }

                override fun onFailure(call: Call<GetExampleByIdResponse>, t: Throwable) {
                    Log.e("retrofit/Example",t.toString())
                }
                })
        } else {
            CookieClient.service.
        }

        binding.homeExampleTitleWordTv.text = tag

        if(favorite_id == 0) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.home_example_main_frm,HomeExplainFragment()).commitAllowingStateLoss()
        }
        else {

        }

    }
}