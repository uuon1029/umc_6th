package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.Retrofit.Request.LoginRequest
import com.example.umc_6th.Retrofit.Request.SignupRequest
import com.example.umc_6th.Retrofit.RetrofitClient
import com.example.umc_6th.Retrofit.LoginResponse
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

        checkUser()

        initSetOnClickListener()
    }

    private fun checkUser() {
        // 자동 로그인의 경우
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

        RetrofitClient.service.postLogin(request).enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<LoginResponse>?,
                response: Response<LoginResponse>?
            ) {
                Log.d("retrofit", response?.code().toString())
                Log.d("retrofit", response?.message().toString())
                Log.d("retrofit", response?.body()?.code.toString())
                val code = response?.code()
                when(code) {
                    200 -> {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.putExtra("auth",response.body()!!.result.accessToken)
                        startActivity(intent)
                    }
                    else -> {
                        showLoginConfirmDialog()
                    }
                }
            }
        })
    }

    private fun showLoginConfirmDialog() {
        val dialog = LoginConfirmDialog(this)
        dialog.show()
    }
}