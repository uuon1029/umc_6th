package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivitySignupCompleteBinding

class SignupCompleteActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupCompleteBinding
    val signupActivity = SignupActivity.signupActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
//
//        binding.signupCompleteBodyTv.text =
//            (intent.getStringExtra("name") + "님,\n회원 가입을 축하합니다.")

        // sign up test
        binding.signupCompleteStartBtn.setOnClickListener {
            signupActivity?.finish()
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}