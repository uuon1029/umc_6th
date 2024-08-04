package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_6th.databinding.ActivityOtherProfileBinding
import com.example.umc_6th.databinding.FragmentProfileCommentBinding

class ProfileCommentFragment : Fragment() {

    lateinit var binding : FragmentProfileCommentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileCommentBinding.inflate(inflater,container,false)

        binding.profileCommentBackIv.setOnClickListener{
            val i = Intent(activity, OtherProfileActivity()::class.java)
            startActivity(i)
        }

        return binding.root
    }
}