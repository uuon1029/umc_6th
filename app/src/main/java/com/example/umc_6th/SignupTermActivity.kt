package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivitySignupTermBinding

class SignupTermActivity : AppCompatActivity() {
    lateinit var binding : ActivitySignupTermBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupTermBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // sign up test
        binding.signupTermNextBtn.setOnClickListener {
            val i = Intent(this,SignupCompleteActivity::class.java)
            startActivity(i)
        }
    }
}