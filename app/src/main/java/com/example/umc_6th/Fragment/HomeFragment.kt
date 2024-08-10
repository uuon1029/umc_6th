package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_6th.Activity.Admin1to1EditActivity
import com.example.umc_6th.Activity.AdminHomeActivity
import com.example.umc_6th.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

//        initOnClickListener()
        return binding.root
    }

//    private fun initOnClickListener(){
//        binding.praticeIv.setOnClickListener{
//            val intent = Intent(activity, AdminHomeActivity()::class.java)
//            startActivity(intent)
//        }
//
//        binding.praticeAdminProfileIv.setOnClickListener{
//            val intent = Intent(activity, AdminUserProfileActivity()::class.java)
//            startActivity(intent)
//        }
//
//        binding.praticeAdmin1to1Tv.setOnClickListener{
//            val intent = Intent(activity, Admin1to1EditActivity()::class.java)
//            startActivity(intent)
//        }
//    }
}

