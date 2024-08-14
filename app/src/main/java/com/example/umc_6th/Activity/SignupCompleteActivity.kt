package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivitySignupCompleteBinding

class SignupCompleteActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupCompleteBinding
    val signupActivity = SignupActivity.signupActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupCompleteBodyTv.text =
            (intent.getStringExtra("name").toString().replace("\n","") + "님,\n회원 가입을 축하합니다.")
        Log.d("signup complete",SignupActivity.name)

        // sign up test
        binding.signupCompleteStartBtn.setOnClickListener {
            signupActivity?.finish()
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}