package com.example.umc_6th

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val login_btn = binding.loginBtn
        val login_user_id_etx = binding.loginUserIdEtx
        val login_user_password_etx = binding.loginUserPasswordEtx
        val login_store_id_btn = binding.loginStoreIdBtn
        val login_auto_btn = binding.loginAutoBtn

        login_btn.setOnClickListener {
            val id = login_user_id_etx.text.toString()
            val pw = login_user_password_etx.text.toString()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        login_store_id_btn.setOnClickListener{
            login_store_id_btn.isSelected = login_store_id_btn.isSelected != true
        }

        login_auto_btn.setOnClickListener{
            login_auto_btn.isSelected = login_auto_btn.isSelected != true
        }
    }
}