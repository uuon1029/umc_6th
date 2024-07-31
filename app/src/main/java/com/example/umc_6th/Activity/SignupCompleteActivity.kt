package com.example.umc_6th

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

        // sign up test
        binding.signupCompleteStartBtn.setOnClickListener {
//            val i = Intent(this, MainActivity::class.java)
//            startActivity(i)
            signupActivity?.finish()
            finish()
        }
    }
}