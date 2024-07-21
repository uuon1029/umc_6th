package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // sign up test
        val i = Intent(this, SignupActivity::class.java)
        startActivity(i)


        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBottomNavigation.setOnItemSelectedListener {
            item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.communityFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, WriteFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.searchFragment -> {
                    val i = Intent(this, MainActivity::class.java)//검색으로 수정 필요
                    startActivity(i)
                    return@setOnItemSelectedListener false
                }

                R.id.configFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, ConfigFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }


}