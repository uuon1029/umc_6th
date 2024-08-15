package com.example.umc_6th

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.DataClass.Suspension
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
    var mainActivity: MainActivity? = MainActivity.mainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainActivity?.finish()
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

        checkUser()

        initSetOnClickListener()
    }

    override fun onResume() {
        super.onResume()
        checkUser()
    }

    override fun onRestart() {
        super.onRestart()
        checkUser()
    }

    private fun checkUser() {
        // 자동 로그인의 경우

        val auth = getSharedPreferences("Auth", MODE_PRIVATE)
        val token = auth.getString("refreshToken", toString()).toString()

        CookieClient.service.postGetAccessToken(token).enqueue(object : Callback<ReissueResponse> {
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
                        Log.d("retrofit/Auto_LOGIN_SUCCESS", response.body()!!.toString())
                        Log.d("retrofit/Auto_LOGIN_response", response.toString())

                        val result = response.body()!!.result

                        LoginSuccess(result.accessToken, result.userId, result.majorId, result.nickName, "")
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
                        //Log.d("retrofit/LOGIN_response.headers", response.headers().get("set-cookie").toString())

                        val result = response.body()!!.result

                        if (binding.loginAutoBtn.isSelected && response.headers().get("set-cookie") != null) {
                            val auth : SharedPreferences = getSharedPreferences("Auth", MODE_PRIVATE)
                            with(auth.edit()) {
                                putString("refreshToken", response.headers().get("set-cookie"))
                                apply()
                            }
                        }

                        MainActivity.suspension = result.suspension
                        LoginSuccess(result.accessToken, result.userId, result.majorId, result.nickName, result.role)
                    }

                    else -> {
                        Log.d("retrofit/LOGIN_FAIL", response?.body()?.isSuccess.toString())
                        showLoginConfirmDialog()
                    }
                }
            }
        })


    }

    private fun LoginSuccess(accessToken:String, user_id:Int, major_id:Int, nickName:String, user_role:String) {
        val spf : SharedPreferences = getSharedPreferences("Auth", MODE_PRIVATE)
        with(spf.edit()) {
            putString("AccessToken", accessToken)
            apply()
        }

        val user : SharedPreferences = getSharedPreferences("User", MODE_PRIVATE)
        with(user.edit()) {
            putInt("user_id", user_id)
            putInt("major_id", major_id)
            putString("nickName", nickName)
            if(user_role!=""){
                putString("userRole", user_role)
            }
            apply()
        }
        Log.d("retrofit/LOGIN_USER", listOf(user_id,major_id,nickName).toString())

        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
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