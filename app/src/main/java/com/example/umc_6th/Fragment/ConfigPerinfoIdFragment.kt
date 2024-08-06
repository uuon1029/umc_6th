package com.example.umc_6th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_6th.databinding.FragmentConfigPerinfoIdBinding

class ConfigPerinfoIdFragment : Fragment() {

    lateinit var binding : FragmentConfigPerinfoIdBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfigPerinfoIdBinding.inflate(inflater,container,false)

        binding.configPerinfoIdBackIv.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigPerinfoFragment()).commitAllowingStateLoss()
        }

        return binding.root
    }
}