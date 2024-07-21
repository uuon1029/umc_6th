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

        initSetOnClickListener()
    }

    private fun initSetOnClickListener() {
        // sign up test
        binding.signupTermNextBtn.setOnClickListener {
            val i = Intent(this,SignupCompleteActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.signupTermAllAgree.setOnClickListener {
            binding.signupTermAllAgreeIv.setImageResource(R.drawable.ic_check_on)
            binding.signupTermServiceAgreeIv.setImageResource(R.drawable.ic_check_on)
            binding.signupTermUseAgreeIv.setImageResource(R.drawable.ic_check_on)
            binding.signupTerm3AgreeIv.setImageResource(R.drawable.ic_check_on)
        }

        binding.signupTermServiceAgree.setOnClickListener {
            binding.signupTermServiceAgreeIv.setImageResource(R.drawable.ic_check_on)
        }
        binding.signupTermUseAgree.setOnClickListener {
            binding.signupTermUseAgreeIv.setImageResource(R.drawable.ic_check_on)
        }
        binding.signupTerm3Agree.setOnClickListener {
            binding.signupTerm3AgreeIv.setImageResource(R.drawable.ic_check_on)
        }

    }
}