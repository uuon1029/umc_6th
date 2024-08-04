package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.umc_6th.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    val profile = OtherProfileActivity.profile
    val userId = OtherProfileActivity.userId

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initBottomNavigation()
        callCommunity()
        // sign up test
//        val i = Intent(this, SignupActivity::class.java)
//        startActivity(i)
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
                        .replace(R.id.main_frm, CommunityFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.searchFragment -> {
                    val i = Intent(this, SearchActivity::class.java)//검색으로 수정 필요
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

    private fun callCommunity(){
        if(profile != null) {
            binding.mainBottomNavigation.selectedItemId = R.id.communityFragment
            if(profile == 1) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, ProfileBoardFragment())
                    .commitAllowingStateLoss()
            }

            if(profile == 2) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, ProfileCommentFragment())
                    .commitAllowingStateLoss()
            }

            if(profile == 3) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, CommunityFragment())
                    .commitAllowingStateLoss()
            }

        }
    }

}