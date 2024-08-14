package com.example.umc_6th

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.FindAccountResponse
import com.example.umc_6th.Retrofit.Request.SignupRequest
import com.example.umc_6th.Retrofit.RetrofitClient
import com.example.umc_6th.Retrofit.SignupResponse
import com.example.umc_6th.Retrofit.okHttpClient
import com.example.umc_6th.databinding.ActivityMainBinding
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.RequestBody
import okio.IOException

class MainActivity : AppCompatActivity() {

    companion object {
        var mainActivity:MainActivity? = null
        var accessToken: String = ""
        var userId: Int = 0
        var account: String = ""
        var majorId: Int = 0
        var nickName: String = "얼렁뚱땅"
    }

    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainActivity = this
        window.statusBarColor = ContextCompat.getColor(this, R.color.main_color)

        val sp = getSharedPreferences("Auth", MODE_PRIVATE)
        accessToken = sp.getString("AccessToken", toString()).toString()
        val user = getSharedPreferences("User", MODE_PRIVATE)
        userId = user.getInt("user_id",0)
        majorId = user.getInt("major_id",0)
        nickName = user.getString("nickName","").toString()

        initBottomNavigation()
    }

    private fun testSignUp() {
        // test retrofit

        val request = SignupRequest(
            name = "테스트",
            nickName = "test3",
            account = "test0001",
            password = "test123!",
            passwordCheck = "test123!",
            major = 1,
            phone = "0109000002"
        )

        RetrofitClient.service.postSignUp(request).enqueue(object : Callback<SignupResponse> {
            override fun onFailure(call: Call<SignupResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<SignupResponse>?,
                response: Response<SignupResponse>?
            ) {
                Log.d("retrofit", response.toString())
                Log.d("retrofit", response?.code().toString())
                Log.d("retrofit", response?.body().toString())
                Log.d("retrofit", response?.message().toString())
                Log.d("retrofit", response?.body()?.result.toString())
            }
        })
    }

    private var backPressedTime: Long = 0L

    override fun onBackPressed() {
        super.onBackPressed()
        if (System.currentTimeMillis() - backPressedTime <= 1000) {
            onDestroy()
        } else {
            backPressedTime = System.currentTimeMillis()
            Toast.makeText(this, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
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



    //    private fun testFindId() {
//        // requestBody를 생성
//        val requestBody = RequestBody.create(
//            "application/json".toMediaType(), "{\"phone\":\"0109000002\"}"
//        )
//
//        // Retrofit 서비스 호출
//        okHttpClient.service.getFindId().enqueue(object : Callback<FindAccountResponse> {
//            override fun onFailure(call: Call<FindAccountResponse>, t: Throwable) {
//                Log.e("retrofit", t.toString())
//            }
//
//            override fun onResponse(
//                call: Call<FindAccountResponse>,
//                response: Response<FindAccountResponse>
//            ) {
//                Log.d("retrofit", response.toString())
//                Log.d("retrofit_code", response.code().toString())
//                Log.d("retrofit_body", response.body().toString())
//                Log.d("retrofit_message", response.message().toString())
//                Log.d("retrofit_result", response.body()?.result.toString())
//            }
//        })
//    }
}