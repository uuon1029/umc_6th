package com.example.umc_6th

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.Request.LoginRequest
import com.example.umc_6th.Retrofit.Request.SignupRequest
import com.example.umc_6th.Retrofit.RetrofitClient
import com.example.umc_6th.Retrofit.LoginResponse
import com.example.umc_6th.Retrofit.Response.ReissueResponse
import com.example.umc_6th.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        checkUser()

        initSetOnClickListener()
    }

    private fun checkUser() {
        // 자동 로그인의 경우
        CookieClient.service.postGetAccessToken().enqueue(object : Callback<ReissueResponse> {
            override fun onFailure(call: Call<ReissueResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<ReissueResponse>?,
                response: Response<ReissueResponse>?
            ) {
                Log.d("retrofit/Auto_LOGIN", response?.code().toString())
                val code = response?.code()
                when (code) {
                    200 -> {
                        Log.d("retrofit/Auto_LOGIN_SUCCESS", response.body()!!.isSuccess.toString())
                        Log.d("retrofit/Auto_LOGIN_response", response.toString())

                        val accessToken = response.body()!!.result.accessToken

                        LoginSuccess(accessToken)
                    }
                }
            }
        })
    }

    private fun initSetOnClickListener() {
        binding.loginBtn.setOnClickListener {
            val id = binding.loginUserIdEtx.text.toString()
            val pw = binding.loginUserPasswordEtx.text.toString()

            login(id,pw)
        }

        binding.loginSearchIdTx.setOnClickListener {
            val intent = Intent(this, FindIdActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.loginSearchPasswordTx.setOnClickListener {
            val intent = Intent(this, FindPwActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.loginSignUpTx.setOnClickListener {
            val intent = Intent(this, SignupTermActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.loginStoreIdBtn.setOnClickListener {
            binding.loginStoreIdBtn.isSelected = !binding.loginStoreIdBtn.isSelected
        }

        binding.loginAutoBtn.setOnClickListener {
            binding.loginAutoBtn.isSelected = !binding.loginAutoBtn.isSelected
        }
    }

    private fun login(id : String, pwd : String) {
        // test retrofit
        val request = LoginRequest(
            account = id,
            password = pwd
        )

        CookieClient.service.postLogin(request).enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<LoginResponse>?,
                response: Response<LoginResponse>?
            ) {
                Log.d("retrofit/LOGIN", response?.code().toString())
                val code = response?.code()
                when (code) {
                    200 -> {
                        Log.d("retrofit/LOGIN_SUCCESS", response.body()!!.isSuccess.toString())
                        Log.d("retrofit/LOGIN_response.headers", response.headers().toString())

                        val accessToken = response.body()!!.result.accessToken

                        LoginSuccess(accessToken)
                    }

                    else -> {
                        Log.d("retrofit/LOGIN_FAIL", response?.body()?.isSuccess.toString())
                        showLoginConfirmDialog()
                    }
                }
            }
        })


    }

    private fun LoginSuccess(accessToken:String) {
        val spf : SharedPreferences = getSharedPreferences("Auth", MODE_PRIVATE)
        with(spf.edit()) {
            putString("AccessToken", accessToken)
            apply()
        }
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun showLoginConfirmDialog() {
        val dialog = LoginConfirmDialog(this)
        dialog.show()
    }

//    private fun testLogin(request: LoginRequest) {
//        RetrofitClient.service.postLogin(request).enqueue(object : Callback<LoginResponse> {
//            override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
//                Log.e("retrofit", t.toString())
//            }
//
//            override fun onResponse(
//                call: Call<LoginResponse>?,
//                response: Response<LoginResponse>?
//            ) {
//                Log.d("retrofit/LOGIN", response?.code().toString())
//                val code = response?.code()
//                when (code) {
//                    200 -> {
//                        Log.d("retrofit/LOGIN_SUCCESS", response.body()!!.isSuccess.toString())
//                        val refreshToken = response.headers()
//                        val accessToken = response.body()!!.result.accessToken
//                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
//                        intent.putExtra("auth", accessToken)
//                        startActivity(intent)
//                    }
//
//                    else -> {
//                        Log.d("retrofit/LOGIN_FAIL", response?.body()?.isSuccess.toString())
//                        showLoginConfirmDialog()
//                    }
//                }
//            }
//        })
//    }
}