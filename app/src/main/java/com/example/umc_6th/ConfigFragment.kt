package com.example.umc_6th

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_6th.databinding.FragmentConfigBinding

class ConfigFragment : Fragment() {

    lateinit var binding: FragmentConfigBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfigBinding.inflate(inflater,container,false)

        binding.configOptionAnnouncementIb.setOnClickListener{
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,AnnouncementFragment()).commitAllowingStateLoss()
        }

        return binding.root
    }
}