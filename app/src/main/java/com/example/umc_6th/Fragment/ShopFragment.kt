package com.example.umc_6th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_6th.databinding.FragmentConfigPerinfoMajorBinding
import com.example.umc_6th.databinding.FragmentShopBinding

class ShopFragment : Fragment() {

    lateinit var binding : FragmentShopBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShopBinding.inflate(inflater,container,false)


        return binding.root
    }
}