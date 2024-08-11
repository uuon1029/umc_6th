package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivitySignupTermBinding

class SignupTermActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupTermBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupTermBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val signup_term_all_agree_iv = binding.signupTermAllAgreeIv
        val signup_term_service_agree_iv = binding.signupTermServiceAgreeIv
        val signup_term_use_agree_iv = binding.signupTermUseAgreeIv
        val signup_term_3_agree_iv = binding.signupTerm3AgreeIv
        val signup_term_next_btn = binding.signupTermNextBtn

        binding.signupTermAllAgree.setOnClickListener {
            val newState = signup_term_all_agree_iv.isSelected != true
            signup_term_all_agree_iv.isSelected = newState
            signup_term_service_agree_iv.isSelected = newState
            signup_term_use_agree_iv.isSelected = newState
            signup_term_3_agree_iv.isSelected = newState
            signup_term_next_btn.isEnabled = newState
        }

        signup_term_service_agree_iv.setOnClickListener {
            signup_term_service_agree_iv.isSelected =
                signup_term_service_agree_iv.isSelected != true
            checkAllAgreed()
        }

        signup_term_use_agree_iv.setOnClickListener {
            signup_term_use_agree_iv.isSelected = signup_term_use_agree_iv.isSelected != true
            checkAllAgreed()
        }

        signup_term_3_agree_iv.setOnClickListener {
            signup_term_3_agree_iv.isSelected = signup_term_3_agree_iv.isSelected != true
            checkAllAgreed()
        }

        signup_term_next_btn.setOnClickListener {
            if (signup_term_service_agree_iv.isSelected && signup_term_use_agree_iv.isSelected && signup_term_3_agree_iv.isSelected) {
                val intent = Intent(this, SignupActivity::class.java)
                startActivity(intent)
            } else {
//                Toast.makeText(this, "모든 약관에 동의해야 합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkAllAgreed() {
        val allAgreed = binding.signupTermServiceAgreeIv.isSelected &&
                binding.signupTermUseAgreeIv.isSelected &&
                binding.signupTerm3AgreeIv.isSelected

        binding.signupTermAllAgreeIv.isSelected = allAgreed
        binding.signupTermNextBtn.isEnabled = allAgreed
    }
}