package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.Retrofit.BoardMainResponse
import com.example.umc_6th.Retrofit.FindAccountResponse
import com.example.umc_6th.Retrofit.Request.FindIdRequest
import com.example.umc_6th.Retrofit.Request.SignupRequest
import com.example.umc_6th.Retrofit.RetrofitClient
import com.example.umc_6th.Retrofit.SignupResponse
import com.example.umc_6th.databinding.ActivityMainBinding
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.RequestBody
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // sign up test
//        val i = Intent(this, SignupActivity::class.java)
//        startActivity(i)

        // test retrofit
        val request = SignupRequest(name = "테스트", nickName = "test3", account = "test0001", password = "test123!", passwordCheck = "test123!", major = 1, phone = "0109000002")

        RetrofitClient.service.postSignUp(request).enqueue(object : Callback<SignupResponse>{
            override fun onFailure(call: Call<SignupResponse>?,t:Throwable?) {
                Log.e("retrofit",t.toString())
            }

            override fun onResponse(
                call: Call<SignupResponse>?,
                response: Response<SignupResponse>?
            ) {
                Log.d("retrofit",response.toString())
                Log.d("retrofit",response?.code().toString())
                Log.d("retrofit",response?.body().toString())
                Log.d("retrofit",response?.message().toString())
                Log.d("retrofit",response?.body()?.result.toString())
            }
        })


        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBottomNavigation.setOnItemSelectedListener {
            item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.communityFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, CommunityFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.searchFragment -> {
                    val i = Intent(this, SearchActivity::class.java)//검색으로 수정 필요
                    startActivity(i)
                    return@setOnItemSelectedListener false
                }

                R.id.configFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, ConfigFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

}