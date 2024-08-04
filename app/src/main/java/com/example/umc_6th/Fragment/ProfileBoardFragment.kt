package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_6th.databinding.FragmentConfigPerinfoMajorBinding
import com.example.umc_6th.databinding.FragmentProfileBoardBinding
import com.example.umc_6th.databinding.FragmentShopBinding

class ProfileBoardFragment : Fragment() {

    var userId = OtherProfileActivity.userId

    lateinit var binding : FragmentProfileBoardBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBoardBinding.inflate(inflater,container,false)

        binding.adminProfileBoardBackIv.setOnClickListener{
            if(userId == null) {
                userId = 1
            }
            OtherProfileActivity.userId = userId
            val i = Intent(activity, OtherProfileActivity()::class.java)
            startActivity(i)
        }

        return binding.root
    }
}