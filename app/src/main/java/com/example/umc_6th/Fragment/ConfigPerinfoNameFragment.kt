package com.example.umc_6th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.example.umc_6th.databinding.FragmentConfigPerinfoNameBinding

class ConfigPerinfoNameFragment : Fragment() {

    lateinit var binding : FragmentConfigPerinfoNameBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfigPerinfoNameBinding.inflate(inflater,container,false)

        binding.configPerinfoNameBackIv.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigPerinfoFragment()).commitAllowingStateLoss()
        }

        return binding.root
    }
}