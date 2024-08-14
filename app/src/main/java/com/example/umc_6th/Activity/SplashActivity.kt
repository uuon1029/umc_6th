package com.example.umc_6th.Activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.umc_6th.LoginActivity
import com.example.umc_6th.MainActivity
import com.example.umc_6th.R
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.Response.ReissueResponse
import com.example.umc_6th.databinding.ActivitySplashBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding

    private var response : ReissueResponse? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.main_color)

        val auth = getSharedPreferences("Auth", MODE_PRIVATE)
        val token = auth.getString("refreshToken", toString()).toString()

        CookieClient.service.postGetAccessToken(token).enqueue(object :
            Callback<ReissueResponse> {
            override fun onFailure(call: Call<ReissueResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<ReissueResponse>?,
                response: Response<ReissueResponse>?
            ) {
                Log.d("retrofit/Auto_LOGIN", response?.code().toString())

                SplashActivity().response = response?.body()
            }
        })

        Handler(Looper.getMainLooper()).postDelayed({
            val code = response?.code?.toInt()

            when (code) {
                200 -> {
                    Log.d("retrofit/Auto_LOGIN_SUCCESS", response!!.isSuccess.toString())
                    Log.d("retrofit/Auto_LOGIN_response", response.toString())

                    val result = response!!.result

                    LoginSuccess(result.accessToken, result.userId, result.majorId, result.nickName)

                }
                else -> {
                    val i = Intent(this@SplashActivity,LoginActivity::class.java)
                    startActivity(i)
                }
            }

            finish()

        }, 2000) // 시간 2초 이후 실행

    }

    private fun LoginSuccess(accessToken:String, user_id:Int, major_id:Int, nickName:String) {
        val spf : SharedPreferences = getSharedPreferences("Auth", MODE_PRIVATE)
        with(spf.edit()) {
            putString("AccessToken", accessToken)
            apply()
        }

        val user : SharedPreferences = getSharedPreferences("User", MODE_PRIVATE)
        with(user.edit()) {
            putInt("user_id", user_id)
            putInt("major_id", major_id)
            putString("nickName",nickName)
            apply()
        }
        Log.d("retrofit/LOGIN_USER", listOf(user_id,major_id,nickName).toString())

        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
    }

}