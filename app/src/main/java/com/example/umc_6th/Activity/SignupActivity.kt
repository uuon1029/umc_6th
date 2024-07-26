package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity(){
    lateinit var binding : ActivitySignupBinding


    companion object{
        var signupActivity : SignupActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initSetOnClickListener()

        signupActivity = this
    }

    private fun initSetOnClickListener() {

        binding.signupBackBtn.setOnClickListener {
            finish()
        }

        // sign up test
        binding.signupWelcomeBtn.setOnClickListener {
            val i = Intent(this,SignupTermActivity::class.java)
            startActivity(i)
        }

    }
}